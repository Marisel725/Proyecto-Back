package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.puntuacion.PuntuacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.puntuacion.PuntuacionSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Puntuacion;
import com.ebikerrent.alquilerbicicletas.entity.Reserva;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.PuntuacionRepository;
import com.ebikerrent.alquilerbicicletas.repository.ReservaRepository;
import com.ebikerrent.alquilerbicicletas.service.IPuntuacionService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class PuntuacionService implements IPuntuacionService {
    private final Logger LOGGER = LoggerFactory.getLogger(PuntuacionService.class);
    private final ModelMapper modelMapper;
    private final ReservaRepository reservaRepository;
    private final PuntuacionRepository puntuacionRepository;

/*
Semántica y claridad: Si el flujo de tu aplicación se centra en la reserva y la puntuación está directamente asociada a una reserva específica, entonces tiene sentido buscar la reserva por su ID. Esto proporciona una semántica clara y fácil de entender para otros desarrolladores que revisen tu código en el futuro.
 */
    @Override
    public PuntuacionSalidaDto puntuarProducto(PuntuacionEntradaDto puntuacionEntradaDto) throws ResourceNotFoundException {
        long valorPuntuacion = puntuacionEntradaDto.getValor();
        if (valorPuntuacion < 1 || valorPuntuacion > 5) {
            LOGGER.error("El valor de la puntuación debe estar entre 1 y 5.");
            throw new ResourceNotFoundException("El valor de la puntuación debe estar entre 1 y 5.");
        }
        Puntuacion puntuacion = modelMapper.map(puntuacionEntradaDto, Puntuacion.class);

        Reserva reserva= reservaRepository.findById(puntuacionEntradaDto.getReservaId()).orElse(null);
        if (reserva == null){
            LOGGER.error("No se encontró el id de la reserva en la BDD");
            throw  new ResourceNotFoundException("No se encontró el id de la reserva en la BDD");
        }
        if (reserva.getPuntuacion() != null){
            LOGGER.info("La reserva ya tiene una puntuación asociada.");
            throw new ResourceNotFoundException("La reserva ya tiene una puntuación asociada.");
        }
        puntuacion = puntuacionRepository.save(puntuacion);
        reserva.setPuntuacion(puntuacion);
        reservaRepository.save(reserva);
        LOGGER.info("Se puntuó correctamente");
        return modelMapper.map(puntuacion, PuntuacionSalidaDto.class);
    }


}
