package com.buyNotes.controller;

import com.buyNotes.dto.ListaDTO;
import com.buyNotes.mapper.ListaMapper;
import com.buyNotes.model.Listas;
import com.buyNotes.model.ProductosLista;
import com.buyNotes.service.ListasService;
import com.buyNotes.service.ProductoFavoritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/listas")
@CrossOrigin(origins = "http://localhost:4200")
public class ListasController {

    private final ListasService listasService;
    private final ListaMapper listaMapper;
    private final ProductoFavoritoService favService;

    /* ---------- LECTURA ---------- */

    @GetMapping
    public ResponseEntity<?> listarMisListas(@RequestAttribute("userId") Long userId) {
        try {
            List<ListaDTO> dtos = listasService.obtenerListasDelUsuario(userId).stream()
                    .map(listaMapper::toDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerLista(@RequestAttribute("userId") Long userId,
                                          @PathVariable Long id) {
        try {
            return ResponseEntity.ok(listaMapper.toDTO(listasService.obtenerLista(userId, id)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* ---------- CREAR / EDITAR / BORRAR LISTA ---------- */

    @PostMapping
    public ResponseEntity<?> crearLista(@RequestAttribute("userId") Long userId,
                                        @RequestBody Listas lista) {
        try {
            return ResponseEntity.ok(listaMapper.toDTO(listasService.crearLista(userId, lista)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> renombrarLista(@RequestAttribute("userId") Long userId,
                                            @PathVariable Long id,
                                            @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(
                    listaMapper.toDTO(listasService.renombrarLista(userId, id, body.get("nombre")))
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/config")
    public ResponseEntity<?> actualizarConfig(@RequestAttribute("userId") Long userId,
                                              @PathVariable Long id,
                                              @RequestBody Map<String, Boolean> body) {
        try {
            return ResponseEntity.ok(listaMapper.toDTO(
                    listasService.actualizarConfig(userId, id,
                            body.get("ordenAscendente"),
                            body.get("mostrarPrecios"))
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarLista(@RequestAttribute("userId") Long userId,
                                           @PathVariable Long id) {
        try {
            listasService.eliminarLista(userId, id);
            return ResponseEntity.ok("Lista eliminada correctamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* ---------- ITEMS ---------- */

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

    @PostMapping("/{idDestino}/items/copiar")
    public ResponseEntity<?> copiarItems(@RequestAttribute("userId") Long userId,
                                         @PathVariable Long idDestino,
                                         @RequestBody List<Long> ids) {
        try {
            return ResponseEntity.ok(listasService.copiarProductos(userId, idDestino, ids));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/{id}/items/{productoId}")
    public ResponseEntity<?> editarItem(@RequestAttribute("userId") Long userId,
                                        @PathVariable Long id,
                                        @PathVariable Long productoId,
                                        @RequestBody ProductosLista cambios) {
        try {
            return ResponseEntity.ok(listasService.editarProducto(userId, id, productoId, cambios));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

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


    @PostMapping("/{idLista}/items/desde-favorito/{idFav}")
    public ResponseEntity<?> añadirDesdeFavorito(@RequestAttribute("userId") Long userId,
                                                 @PathVariable Long idLista,
                                                 @PathVariable Long idFav) {
        try {
            return ResponseEntity.ok(favService.añadirALista(userId, idLista, idFav));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
