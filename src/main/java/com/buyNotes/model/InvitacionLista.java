package com.buyNotes.model;

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

    @ManyToOne
    @JoinColumn(name = "lista_id")
    private Listas lista; // A qué lista se le invita

    @ManyToOne
    @JoinColumn(name = "invitado_id")
    private Usuario invitado; // El usuario que recibe la invitación

    @ManyToOne
    @JoinColumn(name = "anfitrion_id")
    private Usuario anfitrion; // El usuario que envía la invitación

    private LocalDateTime fechaInvitacion = LocalDateTime.now();
}