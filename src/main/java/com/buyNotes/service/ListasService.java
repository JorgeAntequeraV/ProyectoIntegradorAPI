package com.buyNotes.service;

import com.buyNotes.model.Listas;
import com.buyNotes.model.ProductosLista;
import com.buyNotes.model.Usuario;
import com.buyNotes.repository.ListasRepository;
import com.buyNotes.repository.ProductosRepository;
import com.buyNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListasService {

    private final ListasRepository listasRepo;
    private final UsuarioRepository usuarioRepo;
    private final ProductosRepository productosRepo;

    @Transactional
    public Listas crearLista(Long userId, Listas nuevaLista) {
        Usuario usuario = usuarioRepo.getUsuarioById(userId);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado");

        nuevaLista.setTagAmigoCreador(usuario.getTagAmigo());
        nuevaLista.getUsuariosConAcceso().add(usuario);

        return listasRepo.save(nuevaLista);
    }

    @Transactional
    public void eliminarLista(Long userId, Long listaId) {
        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));

        // Verificar que el usuario tiene acceso a la lista
        boolean tieneAcceso = lista.getUsuariosConAcceso().stream()
                .anyMatch(u -> u.getId().equals(userId));

        if (!tieneAcceso) throw new RuntimeException("No tienes permiso para eliminar esta lista");

        listasRepo.delete(lista);
    }

    @Transactional
    public ProductosLista añadirProducto(Long userId, Long listaId, ProductosLista producto) {
        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));

        // Validación de seguridad
        if (lista.getUsuariosConAcceso().stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new RuntimeException("No tienes acceso a esta lista");
        }

        producto.setLista(lista);
        return productosRepo.save(producto);
    }

    @Transactional
    public void quitarProducto(Long userId, Long listaId, Long productoId) {
        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));

        // Validación de seguridad
        if (lista.getUsuariosConAcceso().stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new RuntimeException("No tienes acceso a esta lista");
        }

        ProductosLista producto = productosRepo.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (!producto.getLista().getId().equals(listaId)) {
            throw new IllegalArgumentException("El producto no pertenece a esta lista");
        }

        productosRepo.delete(producto);
    }
}