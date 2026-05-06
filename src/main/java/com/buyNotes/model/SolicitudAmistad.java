package com.buyNotes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "solicitudes_amistad")
public class SolicitudAmistad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "remitente_id")
    private Usuario remitente; // Quién envía la solicitud

    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario; // Quién la recibe

    private LocalDateTime fechaCreacion = LocalDateTime.now();
}