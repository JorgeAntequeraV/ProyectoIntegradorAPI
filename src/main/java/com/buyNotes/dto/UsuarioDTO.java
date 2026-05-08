package com.buyNotes.dto;

import com.buyNotes.model.enums.Rol;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioDTO {
	private Long id;
	
    private String nombre;
    private String email;
    private String contrasena;
    private String telefono;
	
	private String nombreUsuario;
	private String tagAmigo;

    private List<UsuarioDTO> amigos;
    private List<SolicitudAmistadDTO> solicitudesPendientes;
    private List<InvitacionListaDTO> invitacionesListas;
	
	private Rol rol; 
}

