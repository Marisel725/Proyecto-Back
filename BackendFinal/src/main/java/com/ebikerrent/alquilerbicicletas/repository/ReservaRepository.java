package com.ebikerrent.alquilerbicicletas.repository;

import com.ebikerrent.alquilerbicicletas.entity.Reserva;
import com.ebikerrent.alquilerbicicletas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    Reserva findByProductoId(Long productoId);
    List<Reserva> findByUsuarioId(Usuario usuario);


}
