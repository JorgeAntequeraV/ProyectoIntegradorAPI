package com.buyNotes.model;





import com.buyNotes.model.enums.Rol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.AllArgsConstructor.AnyAnnotation;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Usuarios")
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
    private Boolean temaOscuro = false;

    private String nombreUsuario;
    
    @Column(unique = true)
    private String tagAmigo;

    @ManyToMany(mappedBy = "usuariosConAcceso")
    private List<Listas> misListas = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "supermercadosUsuario",
            joinColumns = @JoinColumn(name = "usuario_id")
    )
    @Column(name = "supermercado")
    private List<String> supermercados = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "usuario_amigos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "amigo_id")
    )
    private List<Usuario> amigos = new ArrayList<>();

    // 2. Solicitudes de amistad recibidas
    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SolicitudAmistad> solicitudesAmistadRecibidas = new ArrayList<>();

    // 3. Invitaciones a listas recibidas
    @OneToMany(mappedBy = "invitado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvitacionLista> invitacionesListaRecibidas = new ArrayList<>();

    private Rol rol = Rol.USER; // Por defecto "USER"


}
