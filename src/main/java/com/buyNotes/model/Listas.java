package com.buyNotes.model;

import java.util.ArrayList;
import java.util.List;

import com.buyNotes.model.enums.Rol;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor.AnyAnnotation;

@Data
@Entity
@Table(name = "Listas")
@NoArgsConstructor
@AllArgsConstructor
public class Listas {

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosLista> productos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "listas_accesos",
            joinColumns = @JoinColumn(name = "lista_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> usuariosConAcceso = new ArrayList<>();

    private String nombre;
    private String supermercado;

    private String tagAmigoCreador;

    private Boolean ordenAscendente = true;
    private Boolean mostrarPrecios = false;

    
    
}
