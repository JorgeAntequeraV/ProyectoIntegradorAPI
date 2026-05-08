package com.buyNotes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "productos_favoritos")
@NoArgsConstructor
@AllArgsConstructor
public class ProductoFavorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private String nombre;
    private String cantidad;
    private String unidadMedida;
    private Double precio;
}
