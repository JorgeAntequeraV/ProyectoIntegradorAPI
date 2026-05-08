package com.buyNotes.service;

import com.buyNotes.model.Listas;
import com.buyNotes.model.ProductoFavorito;
import com.buyNotes.model.ProductosLista;
import com.buyNotes.model.Usuario;
import com.buyNotes.repository.ListasRepository;
import com.buyNotes.repository.ProductoFavoritoRepository;
import com.buyNotes.repository.ProductosRepository;
import com.buyNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoFavoritoService {

    private final ProductoFavoritoRepository favRepo;
    private final UsuarioRepository usuarioRepo;
    private final ListasRepository listasRepo;
    private final ProductosRepository productosRepo;

    @Transactional
    public List<ProductoFavorito> listar(Long userId) {
        return favRepo.findByUsuarioId(userId);
    }

    @Transactional
    public ProductoFavorito crear(Long userId, ProductoFavorito fav) {
        Usuario u = usuarioRepo.getUsuarioById(userId);
        if (u == null) throw new IllegalArgumentException("Usuario no encontrado");
        if (fav.getNombre() == null || fav.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        fav.setUsuario(u);
        return favRepo.save(fav);
    }

    @Transactional
    public ProductoFavorito editar(Long userId, Long favId, ProductoFavorito cambios) {
        ProductoFavorito fav = favRepo.findById(favId)
                .orElseThrow(() -> new IllegalArgumentException("Favorito no encontrado"));
        if (!fav.getUsuario().getId().equals(userId))
            throw new RuntimeException("Ese favorito no es tuyo");

        if (cambios.getNombre() != null) fav.setNombre(cambios.getNombre());
        if (cambios.getCantidad() != null) fav.setCantidad(cambios.getCantidad());
        if (cambios.getUnidadMedida() != null) fav.setUnidadMedida(cambios.getUnidadMedida());
        if (cambios.getPrecio() != null) fav.setPrecio(cambios.getPrecio());
        return favRepo.save(fav);
    }

    @Transactional
    public void eliminar(Long userId, Long favId) {
        ProductoFavorito fav = favRepo.findById(favId)
                .orElseThrow(() -> new IllegalArgumentException("Favorito no encontrado"));
        if (!fav.getUsuario().getId().equals(userId))
            throw new RuntimeException("Ese favorito no es tuyo");
        favRepo.delete(fav);
    }

    @Transactional
    public ProductosLista añadirALista(Long userId, Long listaId, Long favId) {
        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));
        if (lista.getUsuariosConAcceso().stream().noneMatch(u -> u.getId().equals(userId)))
            throw new RuntimeException("No tienes acceso a esta lista");

        ProductoFavorito fav = favRepo.findById(favId)
                .orElseThrow(() -> new IllegalArgumentException("Favorito no encontrado"));
        if (!fav.getUsuario().getId().equals(userId))
            throw new RuntimeException("Ese favorito no es tuyo");

        ProductosLista nuevo = new ProductosLista();
        nuevo.setLista(lista);
        nuevo.setNombre(fav.getNombre());
        nuevo.setCantidad(fav.getCantidad());
        nuevo.setUnidadMedida(fav.getUnidadMedida());
        nuevo.setPrecio(fav.getPrecio());
        return productosRepo.save(nuevo);
    }
}
