package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.caracteristica.CaracteristicaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CaracteristicaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.ICaracteristicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/caracteristicas")
@AllArgsConstructor
public class CaracteristicaController {

    private final ICaracteristicaService iCaracteristicaService;

    @Operation(summary = "Registro de una nueva característica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "característica guardada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CaracteristicaSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<CaracteristicaSalidaDto> registrarCaracteristica(@Valid @RequestBody CaracteristicaEntradaDto caracteristicaEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iCaracteristicaService.registrarCaracteristica(caracteristicaEntradaDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Listado de todas las características")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de características obtenida correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CaracteristicaSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("listar")
    public ResponseEntity<List<CaracteristicaSalidaDto>> listarCaracteristicas() {
        return new ResponseEntity<>(iCaracteristicaService.listarCaracteristicas(), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de una característica por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Característica eliminada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Característica no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("eliminar/{id}")
    public  ResponseEntity<String>eliminarCaracteristica(@PathVariable Long id) throws ResourceNotFoundException{
        iCaracteristicaService.eliminarCaracteristica(id);
    return new ResponseEntity<>("Caracteristica eliminada correctamente", HttpStatus.NO_CONTENT);
}
@GetMapping("buscarCaracteristicaPorId/{id}")
    public ResponseEntity<CaracteristicaSalidaDto>buscarCaracteristicaPorId(@PathVariable Long id) throws ResourceNotFoundException{
        return new ResponseEntity<>(iCaracteristicaService.buscarCaracteristicaPorId(id), HttpStatus.OK);
}

@PutMapping("/modificar")
    public ResponseEntity<CaracteristicaSalidaDto> modificarCaracteristica(@Valid @RequestBody CaracteristicaModificacionEntradaDto caracteristicaModificacionEntradaDto) throws ResourceNotFoundException{
        return new ResponseEntity<>(iCaracteristicaService.modificarCaracteristica(caracteristicaModificacionEntradaDto), HttpStatus.OK);
}

}
