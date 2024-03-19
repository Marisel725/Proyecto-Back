package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.reserva.ReservaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.reserva.ReservaSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reservas")
public class ReservaController {
    private final IReservaService iReservaService;

    public ReservaController(IReservaService iReservaService) {
        this.iReservaService = iReservaService;
    }
    @PostMapping("/registrar")
    public ResponseEntity<ReservaSalidaDto> registrarReserva(@Valid @RequestBody ReservaEntradaDto reservaEntradaDto) throws ResourceNotFoundException, ResourceNotFoundException {
        return new ResponseEntity<>(iReservaService.registrarReserva(reservaEntradaDto) , HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ReservaSalidaDto>> listarTodasLasReservas(){
        return new ResponseEntity<>(iReservaService.listarReservas(), HttpStatus.OK);
    }
}
