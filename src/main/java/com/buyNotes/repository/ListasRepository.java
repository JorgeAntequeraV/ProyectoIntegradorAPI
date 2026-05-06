package com.buyNotes.repository;

import com.buyNotes.model.Listas;
import com.buyNotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ListasRepository  extends JpaRepository<Listas, Long> {
    // Listas a las que el usuario tiene acceso (propias + compartidas aceptadas)
    List<Listas> findByUsuariosConAccesoId(Long usuarioId);
}
