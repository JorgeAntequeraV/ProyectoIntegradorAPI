package com.buyNotes.controller;

import com.buyNotes.mapper.SolicitudAmistadMapper;
import com.buyNotes.model.Usuario;
import com.buyNotes.service.SolicitudAmistadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/amistad")
@CrossOrigin(origins = "http://localhost:4200")
public class AmistadController {

    private final SolicitudAmistadService amistadService;
    private final SolicitudAmistadMapper solicitudMapper;

    @PostMapping("/solicitudes")
    public ResponseEntity<?> enviar(@RequestAttribute("userId") Long userId,
                                    @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(solicitudMapper.toDTO(
                    amistadService.enviar(userId, body.get("tagDestinatario"))
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/solicitudes")
    public ResponseEntity<?> listarSolicitudes(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity.ok(amistadService.listarPendientes(userId).stream()
                    .map(solicitudMapper::toDTO)
                    .collect(Collectors.toList()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/solicitudes/{id}/aceptar")
    public ResponseEntity<?> aceptar(@RequestAttribute("userId") Long userId,
                                     @PathVariable Long id) {
        try {
            amistadService.aceptar(userId, id);
            return ResponseEntity.ok("Solicitud aceptada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/solicitudes/{id}")
    public ResponseEntity<?> rechazar(@RequestAttribute("userId") Long userId,
                                      @PathVariable Long id) {
        try {
            amistadService.rechazar(userId, id);
            return ResponseEntity.ok("Solicitud rechazada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarAmigos(@RequestAttribute("userId") Long userId) {
        try {
            // Devolvemos sólo info ligera — evitar exponer contraseñas, etc.
            List<Map<String, Object>> resp = amistadService.listarAmigos(userId).stream()
                    .map(a -> Map.of(
                            "id", (Object) a.getId(),
                            "nombreUsuario", a.getNombreUsuario(),
                            "tagAmigo", a.getTagAmigo()
                    ))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{idAmigo}")
    public ResponseEntity<?> eliminarAmigo(@RequestAttribute("userId") Long userId,
                                           @PathVariable Long idAmigo) {
        try {
            amistadService.eliminarAmigo(userId, idAmigo);
            return ResponseEntity.ok("Amigo eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
