package com.buyNotes.controller;

import com.buyNotes.model.Listas;
import com.buyNotes.model.ProductosLista;
import com.buyNotes.service.ListasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/listas")
@CrossOrigin(origins = "http://localhost:4200")
public class ListasController {

    private final ListasService listasService;

    // Crear una lista nueva
    @PostMapping
    public ResponseEntity<?> crearLista(@RequestAttribute("userId") Long userId, @RequestBody Listas lista) {
        try {
            return ResponseEntity.ok(listasService.crearLista(userId, lista));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Eliminar una lista
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLista(@RequestAttribute("userId") Long userId, @PathVariable Long id) {
        try {
            listasService.eliminarLista(userId, id);
            return ResponseEntity.ok("Lista eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Añadir un item a una lista específica
    @PostMapping("/{id}/items")
    public ResponseEntity<?> añadirItem(@RequestAttribute("userId") Long userId,
                                        @PathVariable Long id,
                                        @RequestBody ProductosLista producto) {
        try {
            return ResponseEntity.ok(listasService.añadirProducto(userId, id, producto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Quitar un item de una lista
    @DeleteMapping("/{id}/items/{productoId}")
    public ResponseEntity<?> quitarItem(@RequestAttribute("userId") Long userId,
                                        @PathVariable Long id,
                                        @PathVariable Long productoId) {
        try {
            listasService.quitarProducto(userId, id, productoId);
            return ResponseEntity.ok("Producto eliminado de la lista");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}