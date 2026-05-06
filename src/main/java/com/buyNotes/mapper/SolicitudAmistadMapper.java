package com.buyNotes.mapper;

import com.buyNotes.dto.SolicitudAmistadDTO;
import com.buyNotes.model.SolicitudAmistad;
import org.springframework.stereotype.Component;

@Component
public class SolicitudAmistadMapper {
    public SolicitudAmistadDTO toDTO(SolicitudAmistad solicitud) {
        if (solicitud == null) return null;
        SolicitudAmistadDTO dto = new SolicitudAmistadDTO();
        dto.setId(solicitud.getId());
        dto.setTagRemitente(solicitud.getRemitente().getTagAmigo());
        dto.setNombreRemitente(solicitud.getRemitente().getNombreUsuario());
        dto.setFechaCreacion(solicitud.getFechaCreacion());
        return dto;
    }
}