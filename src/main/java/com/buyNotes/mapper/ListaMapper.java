package com.buyNotes.mapper;

import com.buyNotes.dto.ListaDTO;
import com.buyNotes.model.Listas;
import com.buyNotes.model.ProductosLista;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListaMapper {

    public ListaDTO toDTO(Listas lista) {
        if (lista == null) return null;
        ListaDTO dto = new ListaDTO();
        dto.setId(lista.getId());
        dto.setNombre(lista.getNombre());
        dto.setSupermercado(lista.getSupermercado());
        dto.setTagAmigoCreador(lista.getTagAmigoCreador());
        dto.setOrdenAscendente(lista.getOrdenAscendente());
        dto.setMostrarPrecios(lista.getMostrarPrecios());

        // Productos ordenados por nombre
        boolean asc = Boolean.TRUE.equals(lista.getOrdenAscendente());
        Comparator<ProductosLista> cmp = Comparator.comparing(
                p -> p.getNombre() == null ? "" : p.getNombre().toLowerCase()
        );
        if (!asc) cmp = cmp.reversed();

        List<ProductosLista> productosOrdenados = lista.getProductos().stream()
                .sorted(cmp)
                .collect(Collectors.toList());
        dto.setProductos(productosOrdenados);

        if (Boolean.TRUE.equals(lista.getMostrarPrecios())) {
            double total = lista.getProductos().stream()
                    .filter(p -> p.getPrecio() != null)
                    .mapToDouble(ProductosLista::getPrecio)
                    .sum();
            dto.setTotal(total);
        }
        return dto;
    }
}
