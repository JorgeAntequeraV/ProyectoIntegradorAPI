package com.buyNotes.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "PorDefecto")
@NoArgsConstructor
@AllArgsConstructor
public class PorDefecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "por_defecto_productos",
            joinColumns = @JoinColumn(name = "por_defecto_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    List<Productos> productosSugeridos = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "por_defecto_supermercados",
            joinColumns = @JoinColumn(name = "por_defecto_id")
    )
    @Column(name = "supermercado")
    List<String> supermercadosSugeridos = new ArrayList<>();
}
