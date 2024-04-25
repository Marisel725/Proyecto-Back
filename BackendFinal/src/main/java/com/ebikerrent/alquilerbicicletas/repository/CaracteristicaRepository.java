package com.ebikerrent.alquilerbicicletas.repository;

import com.ebikerrent.alquilerbicicletas.entity.Caracteristica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaracteristicaRepository extends JpaRepository<Caracteristica,Long> {
    Caracteristica findByNombre(String nombre);
}
