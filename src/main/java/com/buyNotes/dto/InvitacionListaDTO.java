package com.buyNotes.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InvitacionListaDTO {
    private Long id;
    private Long listaId;
    private String nombreLista;
    private String nombreAnfitrion;
    private LocalDateTime fechaInvitacion;
}