package com.buyNotes.mapper;

import com.buyNotes.dto.InvitacionListaDTO;
import com.buyNotes.model.InvitacionLista;
import org.springframework.stereotype.Component;

@Component
public class InvitacionListaMapper {
    public InvitacionListaDTO toDTO(InvitacionLista invitacion) {
        if (invitacion == null) return null;
        InvitacionListaDTO dto = new InvitacionListaDTO();
        dto.setId(invitacion.getId());
        dto.setListaId(invitacion.getLista().getId());
        dto.setNombreLista(invitacion.getLista().getNombre());
        dto.setNombreAnfitrion(invitacion.getAnfitrion().getNombreUsuario());
        dto.setFechaInvitacion(invitacion.getFechaInvitacion());
        return dto;
    }
}