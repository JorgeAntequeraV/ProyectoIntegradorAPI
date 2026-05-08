package com.buyNotes.repository;

import com.buyNotes.model.ProductoFavorito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoFavoritoRepository extends JpaRepository<ProductoFavorito, Long> {
    List<ProductoFavorito> findByUsuarioId(Long usuarioId);
}
