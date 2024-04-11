package com.ebikerrent.alquilerbicicletas.repository;

import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import com.ebikerrent.alquilerbicicletas.entity.Imagen;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    Producto findByNombre(String nombre);

    Producto findByImagenes(Imagen idImagen);

    List<Producto> findAllByNombreContaining(String nombre);

    @Query("SELECT p.id FROM Producto p")
    List<Long> findAllIds();

    List<Producto> findAllByCategoria(Categoria categoria);
}