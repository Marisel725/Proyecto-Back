package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.puntuacion.PuntuacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.puntuacion.PuntuacionSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;

public interface IPuntuacionService {
    PuntuacionSalidaDto puntuarProducto (PuntuacionEntradaDto puntuacionEntradaDto) throws ResourceNotFoundException;
}
