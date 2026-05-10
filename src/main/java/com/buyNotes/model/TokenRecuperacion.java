package com.buyNotes.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tokens_recuperacion")
public class TokenRecuperacion {

    public enum Tipo { RESET_PASSWORD, CAMBIO_EMAIL, VERIFY_EMAIL }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Tipo tipo;

    private String emailNuevo;       // sólo para CAMBIO_EMAIL
    private LocalDateTime expiracion;
    private boolean usado = false;
}
