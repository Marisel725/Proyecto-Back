package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ImagenModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImagenEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.imagen.ImagenSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IImagenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/imagen")
public class ImagenController {
    private final IImagenService iImagenService;

    public ImagenController(IImagenService iImagenService) {
        this.iImagenService = iImagenService;
    }

    @Operation(summary = "Registro de una nueva imagen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Imagen guardada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<ImagenSalidaDto> registrarImagen(@Valid @RequestBody ImagenEntradaDto imagenEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iImagenService.registrarImagen(imagenEntradaDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Listado de todas las imágenes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de imágenes obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<ImagenSalidaDto>> listarImagenes() {
        return new ResponseEntity<>(iImagenService.listarImagenes(), HttpStatus.OK);
    }

    @Operation(summary = "Modificación de una imagen")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen actualizada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ImagenSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error",
                    content = @Content)
    })
    @PutMapping("/modificar")
    public ResponseEntity<ImagenSalidaDto> modificarImagen(@Valid @RequestBody ImagenModificacionEntradaDto imagenModificacionEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iImagenService.modificarImagen(imagenModificacionEntradaDto), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de una imagen por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imagen eliminada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        iImagenService.eliminarImagen(id);
        return new ResponseEntity<>("Imagen eliminado correctamente", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Búsqueda de una imagen por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagen obtenida correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Imagen no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ImagenSalidaDto> buscarImagenPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iImagenService.buscarImagenPorId(id), HttpStatus.OK);
    }

}
