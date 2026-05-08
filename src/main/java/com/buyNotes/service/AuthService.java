package com.buyNotes.service;

import com.buyNotes.model.TokenRecuperacion;
import com.buyNotes.model.Usuario;
import com.buyNotes.repository.TokenRecuperacionRepository;
import com.buyNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepo;
    private final TokenRecuperacionRepository tokenRepo;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.frontend.url:http://localhost:4200}")
    private String frontendUrl;

    //El usuario introduce su nombreUsuario y disparamos el correo de reset.
    @Transactional
    public void forgotPassword(String nombreUsuario) {
        Usuario u = usuarioRepo.getUsuarioByNombreUsuario(nombreUsuario);
        // Respuesta neutra: si no existe, no revelamos nada
        if (u == null) return;
        if (u.getEmail() == null || u.getEmail().isBlank()) return;

        TokenRecuperacion tk = new TokenRecuperacion();
        tk.setUsuario(u);
        tk.setToken(UUID.randomUUID().toString());
        tk.setExpiracion(LocalDateTime.now().plusHours(1));
        tokenRepo.save(tk);

        String link = frontendUrl + "/reset-password?token=" + tk.getToken();
        mailService.enviar(
                u.getEmail(),
                "BuyNotes — Restablecer contraseña",
                "Hola " + u.getNombreUsuario() + ",\n\n" +
                "Has solicitado restablecer tu contraseña. Pulsa este enlace:\n" +
                link + "\n\n" +
                "El enlace caduca en 1 hora. Si no fuiste tú, ignora este correo."
        );
    }

    //El usuario llega desde el link y manda el token + nueva contraseña.
    @Transactional
    public void resetPassword(String token, String nuevaContrasena) {
        if (nuevaContrasena == null || nuevaContrasena.isBlank())
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");

        TokenRecuperacion tk = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));

        if (tk.isUsado())
            throw new IllegalArgumentException("Token ya utilizado");
        if (tk.getExpiracion().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Token caducado");

        Usuario u = tk.getUsuario();
        u.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepo.save(u);

        tk.setUsado(true);
        tokenRepo.save(tk);
    }
}
