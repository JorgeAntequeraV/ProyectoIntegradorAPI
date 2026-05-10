package com.buyNotes.controller;

import com.buyNotes.service.AuthService;
import com.buyNotes.service.GoogleAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;
    private final GoogleAuthService googleAuthService;

    /** Body: { "nombreUsuario": "..." } */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgot(@RequestBody Map<String, String> body) {
        authService.forgotPassword(body.get("nombreUsuario"));
        // Respuesta neutra siempre (no revelamos si el usuario existe)
        return ResponseEntity.ok(Map.of(
                "message", "Si el usuario existe, se ha enviado un enlace de restablecimiento al correo registrado."
        ));
    }

    /** Body: { "token": "...", "nuevaContrasena": "..." } */
    @PostMapping("/reset-password")
    public ResponseEntity<?> reset(@RequestBody Map<String, String> body) {
        try {
            authService.resetPassword(body.get("token"), body.get("nuevaContrasena"));
            return ResponseEntity.ok(Map.of("message", "Contraseña actualizada"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** Body: { "idToken": "..." } — id_token devuelto por Google Sign-In en el cliente. */
    @PostMapping("/google")
    public ResponseEntity<?> google(@RequestBody Map<String, String> body) {
        try {
            String jwt = googleAuthService.loginConGoogle(body.get("idToken"));
            return ResponseEntity.ok(Map.of("token", jwt));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    /** Body: { "token": "..." } — token recibido en el email de verificación. */
    @PostMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestBody Map<String, String> body) {
        try {
            authService.verificarEmail(body.get("token"));
            return ResponseEntity.ok(Map.of("message", "Correo verificado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
