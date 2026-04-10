package com.buyNotes.model;





import com.buyNotes.model.enums.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor.AnyAnnotation;
import lombok.Data;

@Data
@Entity
@Table(name = "Usuarios")
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
    
    private String nombreUsuario;
    
    @Column(unique = true)
    private String tagAmigo;

    private Rol rol = Rol.USER; // Por defecto "USER"
}
