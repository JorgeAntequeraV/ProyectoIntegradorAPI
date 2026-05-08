package com.buyNotes.controller;

import com.buyNotes.model.ProductoFavorito;
import com.buyNotes.service.ProductoFavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/favoritos")
@CrossOrigin(origins = "http://localhost:4200")
public class FavoritosController {

    private final ProductoFavoritoService favService;

    @GetMapping
    public ResponseEntity<?> listar(@RequestAttribute("userId") Long userId) {
        try { return ResponseEntity.ok(favService.listar(userId)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestAttribute("userId") Long userId,
                                   @RequestBody ProductoFavorito fav) {
        try { return ResponseEntity.ok(favService.crear(userId, fav)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestAttribute("userId") Long userId,
                                    @PathVariable Long id,
                                    @RequestBody ProductoFavorito cambios) {
        try { return ResponseEntity.ok(favService.editar(userId, id, cambios)); }
        catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@RequestAttribute("userId") Long userId,
                                      @PathVariable Long id) {
        try {
            favService.eliminar(userId, id);
            return ResponseEntity.ok("Favorito eliminado");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
