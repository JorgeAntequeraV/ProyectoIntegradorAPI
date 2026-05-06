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

    /* ---------- LECTURA ---------- */

    @Transactional
    public List<Listas> obtenerListasDelUsuario(Long userId) {
        return listasRepo.findByUsuariosConAccesoId(userId);
    }

    @Transactional
    public Listas obtenerLista(Long userId, Long listaId) {
        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));
        comprobarAcceso(lista, userId);
        return lista;
    }

    /* ---------- CREAR / EDITAR / BORRAR ---------- */

    @Transactional
    public Listas crearLista(Long userId, Listas nuevaLista) {
        Usuario usuario = usuarioRepo.getUsuarioById(userId);
        if (usuario == null) throw new IllegalArgumentException("Usuario no encontrado");

        nuevaLista.setTagAmigoCreador(usuario.getTagAmigo());
        nuevaLista.getUsuariosConAcceso().add(usuario);
        if (nuevaLista.getOrdenAscendente() == null) nuevaLista.setOrdenAscendente(true);
        if (nuevaLista.getMostrarPrecios() == null) nuevaLista.setMostrarPrecios(false);

        return listasRepo.save(nuevaLista);
    }

    @Transactional
    public Listas renombrarLista(Long userId, Long listaId, String nuevoNombre) {
        Listas lista = obtenerLista(userId, listaId);
        if (nuevoNombre == null || nuevoNombre.isBlank())
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        lista.setNombre(nuevoNombre);
        return listasRepo.save(lista);
    }

    @Transactional
    public Listas actualizarConfig(Long userId, Long listaId, Boolean ordenAscendente, Boolean mostrarPrecios) {
        Listas lista = obtenerLista(userId, listaId);
        if (ordenAscendente != null) lista.setOrdenAscendente(ordenAscendente);
        if (mostrarPrecios != null) lista.setMostrarPrecios(mostrarPrecios);
        return listasRepo.save(lista);
    }

    @Transactional
    public void eliminarLista(Long userId, Long listaId) {
        Listas lista = listasRepo.findById(listaId)
                .orElseThrow(() -> new IllegalArgumentException("Lista no encontrada"));
        comprobarAcceso(lista, userId);
        listasRepo.delete(lista);
    }

    /* ---------- ITEMS ---------- */

    @Transactional
    public ProductosLista añadirProducto(Long userId, Long listaId, ProductosLista producto) {
        Listas lista = obtenerLista(userId, listaId);
        producto.setLista(lista);
        return productosRepo.save(producto);
    }

    @Transactional
    public ProductosLista editarProducto(Long userId, Long listaId, Long productoId, ProductosLista cambios) {
        Listas lista = obtenerLista(userId, listaId);
        ProductosLista producto = productosRepo.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        if (!producto.getLista().getId().equals(lista.getId()))
            throw new IllegalArgumentException("El producto no pertenece a esta lista");

        if (cambios.getNombre() != null) producto.setNombre(cambios.getNombre());
        if (cambios.getCantidad() != null) producto.setCantidad(cambios.getCantidad());
        if (cambios.getUnidadMedida() != null) producto.setUnidadMedida(cambios.getUnidadMedida());
        if (cambios.getPrecio() != null) producto.setPrecio(cambios.getPrecio());

        return productosRepo.save(producto);
    }

    @Transactional
    public void quitarProducto(Long userId, Long listaId, Long productoId) {
        Listas lista = obtenerLista(userId, listaId);
        ProductosLista producto = productosRepo.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        if (!producto.getLista().getId().equals(listaId))
            throw new IllegalArgumentException("El producto no pertenece a esta lista");
        productosRepo.delete(producto);
    }

    /* ---------- HELPERS ---------- */

    private void comprobarAcceso(Listas lista, Long userId) {
        boolean tiene = lista.getUsuariosConAcceso().stream()
                .anyMatch(u -> u.getId().equals(userId));
        if (!tiene) throw new RuntimeException("No tienes acceso a esta lista");
    }

    @Transactional
    public List<ProductosLista> copiarProductos(Long userId, Long idDestino, List<Long> productoIds) {
        Listas destino = obtenerLista(userId, idDestino);
        List<ProductosLista> copiados = new java.util.ArrayList<>();

        for (Long pid : productoIds) {
            ProductosLista original = productosRepo.findById(pid)
                    .orElseThrow(() -> new IllegalArgumentException("Producto " + pid + " no encontrado"));
            // Verificar acceso a la lista origen también
            comprobarAcceso(original.getLista(), userId);

            ProductosLista copia = new ProductosLista();
            copia.setLista(destino);
            copia.setNombre(original.getNombre());
            copia.setCantidad(original.getCantidad());
            copia.setUnidadMedida(original.getUnidadMedida());
            copia.setPrecio(original.getPrecio());
            copiados.add(productosRepo.save(copia));
        }
        return copiados;
    }

}
