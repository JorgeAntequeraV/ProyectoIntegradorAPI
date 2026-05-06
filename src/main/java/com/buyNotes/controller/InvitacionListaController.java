package com.buyNotes.controller;

import com.buyNotes.mapper.InvitacionListaMapper;
import com.buyNotes.service.InvitacionListaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class InvitacionListaController {

    private final InvitacionListaService invitacionService;
    private final InvitacionListaMapper invitacionMapper;

    @PostMapping
    public ResponseEntity<?> invitar(@RequestAttribute("userId") Long userId,
                                     @RequestBody Map<String, Object> body) {
        try {
            Long listaId = Long.valueOf(body.get("idLista").toString());
            String tag = body.get("tagInvitado").toString();
            return ResponseEntity.ok(invitacionMapper.toDTO(invitacionService.invitar(userId, listaId, tag)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listar(@RequestAttribute("userId") Long userId) {
        try {
            return ResponseEntity.ok(
                    invitacionService.listarPendientes(userId).stream()
                            .map(invitacionMapper::toDTO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptar(@RequestAttribute("userId") Long userId,
                                     @PathVariable Long id) {
        try {
            invitacionService.aceptar(userId, id);
            return ResponseEntity.ok("Invitación aceptada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> rechazar(@RequestAttribute("userId") Long userId,
                                      @PathVariable Long id) {
        try {
            invitacionService.rechazar(userId, id);
            return ResponseEntity.ok("Invitación rechazada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
