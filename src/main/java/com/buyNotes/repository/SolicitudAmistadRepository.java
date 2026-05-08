package com.buyNotes.repository;

import com.buyNotes.model.SolicitudAmistad;
import com.buyNotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SolicitudAmistadRepository extends JpaRepository<SolicitudAmistad, Long> {
    List<SolicitudAmistad> findByDestinatario(Usuario destinatario);
    boolean existsByRemitenteAndDestinatario(Usuario remitente, Usuario destinatario);
}