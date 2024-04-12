package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CategoriaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.categoria.CategoriaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.BadRequestException;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.ICategoriaService;
import com.ebikerrent.alquilerbicicletas.service.impl.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categorias")
public class CategoriaController {

    private final ICategoriaService iCategoriaService;
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);

    public CategoriaController(ICategoriaService iCategoriaService) {
        this.iCategoriaService = iCategoriaService;
    }

    @Operation(summary = "Registro de una nueva categoría")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoría guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<CategoriaSalidaDto> registrarCategoria(@Valid @RequestBody CategoriaEntradaDto categoriaEntradaDto) throws DuplicateEntryException, DataIntegrityViolationException {
        return new ResponseEntity<>(iCategoriaService.registrarCategoria(categoriaEntradaDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Listado de todas las categorías")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de categorías obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<CategoriaSalidaDto>> listarCategorias() {
        return new ResponseEntity<>(iCategoriaService.listarCategorias(), HttpStatus.OK);
    }

    @Operation(summary = "Modificación de una categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error",
                    content = @Content)
    })
    @PutMapping("/modificar")
    public ResponseEntity<CategoriaSalidaDto> modificarCategoria(@Valid @RequestBody CategoriaModificacionEntradaDto categoriaModificacion) throws ResourceNotFoundException {
        return new ResponseEntity<>(iCategoriaService.modificarCategoria(categoriaModificacion), HttpStatus.OK);
    }


    @Operation(summary = "Eliminación de una categoría por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) throws ResourceNotFoundException, DataIntegrityViolationException {
        iCategoriaService.eliminarCategoria(id);
        return new ResponseEntity<>("Categoria eliminada correctamente", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Búsqueda de una categoría por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría obtenida correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<CategoriaSalidaDto> buscarCategoriaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iCategoriaService.buscarCategoriaPorId(id), HttpStatus.OK);
    }

}
