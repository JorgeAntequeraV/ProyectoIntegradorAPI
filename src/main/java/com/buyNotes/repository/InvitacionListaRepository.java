package com.buyNotes.repository;

import com.buyNotes.model.InvitacionLista;
import com.buyNotes.model.Listas;
import com.buyNotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitacionListaRepository extends JpaRepository<InvitacionLista, Long> {
    List<InvitacionLista> findByInvitado(Usuario invitado);
    boolean existsByListaAndInvitado(Listas lista, Usuario invitado);
}
