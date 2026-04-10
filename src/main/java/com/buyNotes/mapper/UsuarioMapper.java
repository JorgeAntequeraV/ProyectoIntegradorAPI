package com.buyNotes.mapper;

import org.springframework.stereotype.Component;

import com.buyNotes.dto.UsuarioDTO;
import com.buyNotes.model.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setTagAmigo(usuario.getTagAmigo());
        dto.setRol(usuario.getRol());

        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setContrasena(dto.getContrasena());
        usuario.setTelefono(dto.getTelefono());
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setTagAmigo(dto.getTagAmigo());
        
        if (dto.getRol() != null) {
            usuario.setRol(dto.getRol());
        }

        return usuario;
    }
}