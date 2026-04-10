package com.buyNotes.model;

import java.util.ArrayList;
import java.util.List;

import com.buyNotes.model.enums.Rol;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    
    @OneToMany(mappedBy = "listas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductosLista> productos = new ArrayList<>();
    
    
    
    
    
}
