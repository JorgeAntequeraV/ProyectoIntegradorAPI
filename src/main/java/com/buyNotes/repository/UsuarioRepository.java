package com.buyNotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.buyNotes.model.Usuario;

import java.util.List;
import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByTagAmigo(String tagAmigo);
    Usuario getUsuarioById(Long id);
    Usuario getUsuarioByNombreUsuario(String nombreUsuario);
    Usuario getUsuarioByEmail(String email);
}