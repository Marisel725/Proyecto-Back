package com.ebikerrent.alquilerbicicletas.repository;

import com.ebikerrent.alquilerbicicletas.entity.Puntuacion;
import com.ebikerrent.alquilerbicicletas.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PuntuacionRepository extends JpaRepository <Puntuacion,Long> {
    // Método para buscar puntuaciones por valor
    List<Puntuacion> findByValor(int valor);

    // Método para buscar puntuaciones por reserva
    List<Puntuacion> findByReserva(Reserva reserva);

    // Método personalizado usando consulta JPQL
    @Query("SELECT p FROM Puntuacion p WHERE p.valor > ?1")
    List<Puntuacion> findByValorGreaterThan(int valor);
}
