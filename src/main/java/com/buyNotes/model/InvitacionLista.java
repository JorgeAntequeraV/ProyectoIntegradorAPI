package com.buyNotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "invitaciones_lista")
public class InvitacionLista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "invitado_id")
    private Usuario invitado;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "anfitrion_id")
    private Usuario anfitrion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "lista_id")
    private Listas lista;


    private LocalDateTime fechaInvitacion = LocalDateTime.now();
}