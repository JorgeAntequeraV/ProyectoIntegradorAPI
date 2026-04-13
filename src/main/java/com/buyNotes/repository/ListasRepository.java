package com.buyNotes.repository;

import com.buyNotes.model.Listas;
import com.buyNotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListasRepository  extends JpaRepository<Listas, Long> {
}
