package com.buyNotes.security;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        // Deja pasar sin autenticar rutas públicas que no necesitan token
        if (path.startsWith("/usuarios/login")
                || path.equals("/usuarios/registro")
                || path.equals("/auth/forgot-password")
                || path.equals("/auth/reset-password" )
                || path.equals("/auth/change-email-confirm")


        ) {
            filterChain.doFilter(request, response);
            return;
        }


        final String authHeader = request.getHeader("Authorization");

        // Si no hay token, sigue la cadena (SecurityConfig decidirá si la ruta es pública o no)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                if (jwtUtil.validateToken(jwt)) {

                    String rawRole = jwtUtil.extractRole(jwt);
                    String normalizedRole = (rawRole != null && rawRole.startsWith("ROLE_")) ? rawRole : "ROLE_USER";

                    List<SimpleGrantedAuthority> authorities =
                            Collections.singletonList(new SimpleGrantedAuthority(normalizedRole));

                    UserDetails userDetails = User.withUsername(username).password("").authorities(authorities).build();

                    // extrae userId del token y lo coloca en la request
                    Integer userId = jwtUtil.extractUserId(jwt);
                        if (userId != null) {
                            request.setAttribute("userId", userId);
                    }

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        } catch (Exception e) {
            System.out.println("Token inválido: " + e.getMessage());
            //No bloqueamos, simplemente no autenticamos y SecurityConfig resolverá
        }

        filterChain.doFilter(request, response);
    }
}
