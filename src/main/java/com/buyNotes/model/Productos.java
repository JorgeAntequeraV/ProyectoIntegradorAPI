package com.buyNotes.model;

import com.buyNotes.model.enums.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Productos")
@AllArgsConstructor
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cantidad = "";
    private Double precio = 0.00;
    private Categoria categoria;

    public Productos(String nombre, Categoria categoria) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = "";
        this.precio = 0.00;
    }
}
