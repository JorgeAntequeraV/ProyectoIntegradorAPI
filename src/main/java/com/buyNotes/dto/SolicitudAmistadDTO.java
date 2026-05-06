package com.buyNotes.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SolicitudAmistadDTO {
    private Long id;
    private String tagRemitente;
    private String nombreRemitente;
    private LocalDateTime fechaCreacion;
}