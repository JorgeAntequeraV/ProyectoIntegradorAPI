package com.buyNotes.repository;

import com.buyNotes.model.ProductosLista;
import com.buyNotes.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository  extends JpaRepository<ProductosLista, Long> {
}
