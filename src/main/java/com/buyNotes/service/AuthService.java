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

    @Value("${app.frontend.url:tfg://}")
    private String frontendUrl;

    /* ---------- RESET PASSWORD ---------- */

    /** El usuario introduce su nombreUsuario y disparamos el correo de reset. */
    @Transactional
    public void forgotPassword(String nombreUsuario) {
        Usuario u = usuarioRepo.getUsuarioByNombreUsuario(nombreUsuario);
        if (u == null) return;
        if (u.getEmail() == null || u.getEmail().isBlank()) return;

        TokenRecuperacion tk = new TokenRecuperacion();
        tk.setUsuario(u);
        tk.setToken(UUID.randomUUID().toString());
        tk.setTipo(TokenRecuperacion.Tipo.RESET_PASSWORD);
        tk.setExpiracion(LocalDateTime.now().plusHours(1));
        tokenRepo.save(tk);

        String link = construirLink("reset-password", tk.getToken());
        String html = """
                <!DOCTYPE html>
                <html><body style="font-family: Arial, sans-serif; line-height:1.5; color:#222;">
                  <h2 style="color:#00805f;">BuyNotes</h2>
                  <p>Hola <strong>%s</strong>,</p>
                  <p>Has solicitado restablecer tu contraseña. Pulsa el siguiente botón
                     para abrir la app y elegir una nueva:</p>
                  <p style="margin:24px 0;">
                    <a href="%s"
                       style="background-color:#00F095; color:#000; padding:12px 24px;
                              text-decoration:none; border-radius:6px; font-weight:bold;
                              display:inline-block;">
                      Restablecer contraseña
                    </a>
                  </p>
                  <p>O abre este enlace manualmente:<br>
                    <a href="%s">%s</a>
                  </p>
                  <p style="color:#888; font-size:13px;">
                    El enlace caduca en 1 hora.<br>
                    Si no has solicitado este cambio, ignora este correo.
                  </p>
                </body></html>
                """.formatted(escapeHtml(u.getNombreUsuario()), link, link, link);

        mailService.enviarHtml(u.getEmail(), "BuyNotes — Restablecer contraseña", html);
    }

    /** El usuario llega desde el link y manda el token + nueva contraseña. */
    @Transactional
    public void resetPassword(String token, String nuevaContrasena) {
        if (nuevaContrasena == null || nuevaContrasena.isBlank())
            throw new IllegalArgumentException("La nueva contraseña no puede estar vacía");

        TokenRecuperacion tk = validarToken(token, TokenRecuperacion.Tipo.RESET_PASSWORD);

        Usuario u = tk.getUsuario();
        u.setContrasena(passwordEncoder.encode(nuevaContrasena));
        usuarioRepo.save(u);

        tk.setUsado(true);
        tokenRepo.save(tk);
    }

    /* ---------- VERIFICACIÓN DE EMAIL ---------- */

    /** Genera un token y envía un email de verificación al usuario recién registrado. */
    @Transactional
    public void enviarVerificacionEmail(Usuario u) {
        if (u == null || u.getEmail() == null || u.getEmail().isBlank()) return;

        TokenRecuperacion tk = new TokenRecuperacion();
        tk.setUsuario(u);
        tk.setToken(UUID.randomUUID().toString());
        tk.setTipo(TokenRecuperacion.Tipo.VERIFY_EMAIL);
        tk.setExpiracion(LocalDateTime.now().plusHours(24));
        tokenRepo.save(tk);

        String link = construirLink("verify-email", tk.getToken());
        String html = """
                <!DOCTYPE html>
                <html><body style="font-family: Arial, sans-serif; line-height:1.5; color:#222;">
                  <h2 style="color:#00805f;">BuyNotes</h2>
                  <p>Hola <strong>%s</strong>,</p>
                  <p>Bienvenido a BuyNotes. Para confirmar tu correo electrónico,
                     pulsa el siguiente botón:</p>
                  <p style="margin:24px 0;">
                    <a href="%s"
                       style="background-color:#00F095; color:#000; padding:12px 24px;
                              text-decoration:none; border-radius:6px; font-weight:bold;
                              display:inline-block;">
                      Verificar mi correo
                    </a>
                  </p>
                  <p>O abre este enlace manualmente:<br>
                    <a href="%s">%s</a>
                  </p>
                  <p style="color:#888; font-size:13px;">
                    El enlace caduca en 24 horas.
                  </p>
                </body></html>
                """.formatted(escapeHtml(u.getNombreUsuario()), link, link, link);

        mailService.enviarHtml(u.getEmail(), "BuyNotes — Verifica tu correo", html);
    }

    /** Marca el email como verificado tras pulsar el link del correo. */
    @Transactional
    public void verificarEmail(String token) {
        TokenRecuperacion tk = validarToken(token, TokenRecuperacion.Tipo.VERIFY_EMAIL);

        Usuario u = tk.getUsuario();
        u.setEmailVerificado(true);
        usuarioRepo.save(u);

        tk.setUsado(true);
        tokenRepo.save(tk);
    }

    /* ---------- HELPERS ---------- */

    private TokenRecuperacion validarToken(String token, TokenRecuperacion.Tipo tipoEsperado) {
        TokenRecuperacion tk = tokenRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token inválido"));
        if (tk.isUsado()) throw new IllegalArgumentException("Token ya utilizado");
        if (tk.getTipo() != null && tk.getTipo() != tipoEsperado)
            throw new IllegalArgumentException("Token de tipo incorrecto");
        if (tk.getExpiracion().isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("Token caducado");
        return tk;
    }

    private String construirLink(String path, String token) {
        // Compatible con scheme "tfg://", "https://dominio.com/" o "http://localhost:4200"
        String base = frontendUrl;
        if (!base.endsWith("/")) base += "/";
        return base + path + "?token=" + token;
    }

    private String escapeHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
