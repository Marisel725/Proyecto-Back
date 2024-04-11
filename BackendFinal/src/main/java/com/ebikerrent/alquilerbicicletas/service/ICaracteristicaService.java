package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.caracteristica.CaracteristicaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CaracteristicaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ICaracteristicaService {
    List<CaracteristicaSalidaDto> listarCaracteristicas();
    CaracteristicaSalidaDto registrarCaracteristica(CaracteristicaEntradaDto caracteristicaEntradaDto) throws ResourceNotFoundException;

    CaracteristicaSalidaDto buscarCaracteristicaPorId(Long id) throws ResourceNotFoundException;

    void eliminarCaracteristica(Long id) throws ResourceNotFoundException;


    CaracteristicaSalidaDto modificarCaracteristica (CaracteristicaModificacionEntradaDto caracteristicaModificacionEntradaDto) throws ResourceNotFoundException;
}
