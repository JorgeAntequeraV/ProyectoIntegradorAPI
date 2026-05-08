package com.buyNotes.dto;

import com.buyNotes.model.ProductosLista;
import lombok.Data;

import java.util.List;

@Data
public class ListaDTO {
    private Long id;
    private String nombre;
    private String supermercado;
    private String tagAmigoCreador;
    private Boolean ordenAscendente;
    private Boolean mostrarPrecios;
    private List<ProductosLista> productos;
    private Double total; // sumatorio de precios (sólo se rellena si mostrarPrecios = true)
}
