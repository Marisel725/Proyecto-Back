package com.ebikerrent.alquilerbicicletas.repository;

import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria,Long> {
    Categoria findByTitulo(String titulo);
}
