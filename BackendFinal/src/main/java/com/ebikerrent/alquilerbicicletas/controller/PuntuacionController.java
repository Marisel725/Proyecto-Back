package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.puntuacion.PuntuacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.puntuacion.PuntuacionSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IPuntuacionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/puntuaciones")
public class PuntuacionController {
    private final IPuntuacionService puntuacionService;

    public PuntuacionController(IPuntuacionService puntuacionService) {
        this.puntuacionService = puntuacionService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<PuntuacionSalidaDto> puntuarProducto(@Valid @RequestBody PuntuacionEntradaDto puntuacionEntradaDto) throws ResourceNotFoundException, ResourceNotFoundException {
        PuntuacionSalidaDto puntuacionSalidaDto = puntuacionService.puntuarProducto(puntuacionEntradaDto);
        return new ResponseEntity<>(puntuacionSalidaDto, HttpStatus.CREATED);
    }
}

