package com.buyNotes.controller;

import com.buyNotes.model.Usuario;
import com.buyNotes.model.enums.Rol;
import com.buyNotes.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/usuarios")
    public ResponseEntity<?> listar(@RequestParam(value = "q", required = false) String q) {
        List<Usuario> users = (q == null) ? adminService.listar() : adminService.buscar(q);
        List<Map<String, Object>> resp = users.stream().map(u -> Map.of(
                "id", (Object) u.getId(),
                "nombreUsuario", u.getNombreUsuario(),
                "email", u.getEmail(),
                "tagAmigo", u.getTagAmigo(),
                "rol", u.getRol() == null ? "USER" : u.getRol().name()
        )).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/usuarios/{id}/rol")
    public ResponseEntity<?> cambiarRol(@PathVariable Long id,
                                        @RequestBody Map<String, String> body) {
        try {
            Rol nuevo = Rol.valueOf(body.get("rol"));
            Usuario u = adminService.cambiarRol(id, nuevo);
            return ResponseEntity.ok(Map.of("id", u.getId(), "rol", u.getRol().name()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            adminService.eliminar(id);
            return ResponseEntity.ok(Map.of("message", "Usuario eliminado"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
