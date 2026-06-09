package com.buyNotes.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    // Aquí guardamos un registro de la ip y los datos
    private final Map<String, DatosDePeticion> registroIps = new ConcurrentHashMap<>();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        int tiempoLimite = 180000;
        int maxIntentos = 5;
        String uri = request.getRequestURI();

        //Si tiene / al final se la quitamos, porque los navegadores tratan igual /public que /public/ y como no sabes cómo te va a venir mejor quitarsela
        if (uri.endsWith("/") && uri.length() > 1) {
            uri = uri.substring(0, uri.length() - 1);
        }
        Set<String> rutasProtegidas = Set.of(
                "/usuarios/registro",
                "/usuarios/forgot-password"


        );
        // Checkeamos de donde viene la peticion.
        if (!rutasProtegidas.contains(uri)) {
            filterChain.doFilter(request, response);
            return;
        }




        // Sacamos la ip
        String ipUsuario = request.getHeader("X-Forwarded-For");
        if (ipUsuario == null || ipUsuario.isEmpty()) {
            ipUsuario = request.getRemoteAddr(); // IP directa si no hay proxy/Cloudflare
        }
        //Al coger la ip se vienen 3 ips no sé porqué, pero la que necesitamos es la primera así que hacemos un split y cogemos la primera
        String[] ipPrincipal = ipUsuario.split(",");
        ipUsuario = ipPrincipal[0].trim();

        //Juntamos la ip a la ruta para asegurarnos de que los intentos sean por ruta
        String claveMapa = ipUsuario + "-" + uri;


        // Obtener o crear atómicamente el registro de esa IP
        DatosDePeticion datos = registroIps.computeIfAbsent(claveMapa, k -> new DatosDePeticion());

        // Sincronizar el bloque de validación para evitar race conditions
        synchronized (datos) {
            long tiempoActual = System.currentTimeMillis();
            long tiempoPasado = tiempoActual - datos.tiempoDelPrimerMensaje;


            if (tiempoPasado > tiempoLimite) {
                datos.tiempoDelPrimerMensaje = tiempoActual;
                datos.contadorMensajes = 0;
            }

            if (datos.contadorMensajes >= maxIntentos) {
                response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                response.setContentType("text/plain; charset=UTF-8");
                response.getWriter().write("Has enviado demasiados mensajes. Por favor, espera " + tiempoLimite/60000 +" minutos.");
                return;
            }

            datos.contadorMensajes++;
        }
        filterChain.doFilter(request, response);
    }


    @Scheduled(fixedRate = 600000)
    public void limpiarIpsAntiguas() {
        long tiempoActual = System.currentTimeMillis();

        registroIps.entrySet().removeIf(entry -> {
            DatosDePeticion datos = entry.getValue();
            // Si el tiempo pasado desde su primer mensaje es mayor al límite ya no hace falta guardarlo, por lo que lo sacamos brutalmente de Mapa
            return (tiempoActual - datos.tiempoDelPrimerMensaje) > 300000;
        });
    }


    // Guardar Información
    private static class DatosDePeticion {
        long tiempoDelPrimerMensaje = System.currentTimeMillis();
        int contadorMensajes = 0;
    }
}