package com.buyNotes.repository;

import com.buyNotes.model.PorDefecto;
import com.buyNotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PorDefectoRepository  extends JpaRepository<PorDefecto, Long> {
    PorDefecto getById(Long id);
    PorDefecto findTopByOrderByIdAsc();
}
