package com.buyNotes.service;

import com.buyNotes.model.Usuario;
import com.buyNotes.model.enums.Rol;
import com.buyNotes.repository.UsuarioRepository;
import com.buyNotes.security.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final UsuarioRepository usuarioRepo;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * IDs de cliente OAuth aceptados (separados por coma).
     * Como la app Android puede mandar el id_token emitido por su propio
     * cliente Android o por un cliente Web, conviene aceptar varios.
     */
    @Value("${google.oauth.client-ids:}")
    private String clientIds;

    @Transactional
    public String loginConGoogle(String idTokenString) throws Exception {
        if (idTokenString == null || idTokenString.isBlank()) {
            throw new IllegalArgumentException("Token de Google ausente");
        }
        if (clientIds == null || clientIds.isBlank()) {
            throw new IllegalStateException("google.oauth.client-ids no configurado en el servidor");
        }

        List<String> audiences = Arrays.stream(clientIds.split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .toList();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(audiences)
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new IllegalArgumentException("Token de Google inválido");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();
        String email = payload.getEmail();
        String nombre = (String) payload.get("name");
        Boolean emailVerified = payload.getEmailVerified();

        if (email == null || Boolean.FALSE.equals(emailVerified)) {
            throw new IllegalArgumentException("La cuenta de Google no tiene email verificado");
        }

        // 1) ¿Hay ya un usuario con ese email registrado?
        Usuario u = usuarioRepo.getUsuarioByEmail(email);

        // 2) Si no existe, lo creamos automáticamente
        if (u == null) {
            u = new Usuario();
            u.setEmail(email);
            u.setNombre(nombre != null ? nombre : email);
            u.setNombreUsuario(generarNombreUsuarioUnico(email));
            // Contraseña aleatoria — el usuario nunca la va a usar (entra siempre con Google)
            u.setContrasena(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));
            u.setRol(Rol.USER);
            u.setTagAmigo(usuarioService.generarTagUnico());
            u = usuarioRepo.save(u);
        }

        // 3) Devolvemos un JWT propio de la app
        return jwtUtil.generateToken(u);
    }

    private String generarNombreUsuarioUnico(String email) {
        String base = email.split("@")[0].replaceAll("[^A-Za-z0-9_]", "_");
        if (base.isBlank()) base = "user";
        String candidato = base;
        int i = 1;
        while (usuarioRepo.getUsuarioByNombreUsuario(candidato) != null) {
            candidato = base + "_" + i++;
        }
        return candidato;
    }
}
