package com.buyNotes.service;

import com.buyNotes.model.Usuario;
import com.buyNotes.model.enums.Rol;
import com.buyNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsuarioRepository usuarioRepo;

    public List<Usuario> listar() { return usuarioRepo.findAll(); }

    public List<Usuario> buscar(String q) {
        if (q == null || q.isBlank()) return listar();
        return usuarioRepo
                .findByNombreUsuarioContainingIgnoreCaseOrTagAmigoContainingIgnoreCaseOrEmailContainingIgnoreCase(q, q, q);
    }

    @Transactional
    public Usuario cambiarRol(Long id, Rol nuevoRol) {
        Usuario u = usuarioRepo.getUsuarioById(id);
        if (u == null) throw new IllegalArgumentException("Usuario no encontrado");
        u.setRol(nuevoRol);
        return usuarioRepo.save(u);
    }

    @Transactional
    public void eliminar(Long id) {
        Usuario u = usuarioRepo.getUsuarioById(id);
        if (u == null) throw new IllegalArgumentException("Usuario no encontrado");
        usuarioRepo.delete(u);
    }
}
