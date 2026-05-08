package com.buyNotes.controller;

import com.buyNotes.service.AuthService;
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
}
