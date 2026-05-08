package com.buyNotes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "remitente_id")
    private Usuario remitente;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "destinatario_id")
    private Usuario destinatario;


    private LocalDateTime fechaCreacion = LocalDateTime.now();
}