package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ProductoModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoDisponibleEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoPorCategoria;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IProductoService;
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
@RequestMapping("/productos")
public class ProductoController {

    private final IProductoService iProductoService;

    public ProductoController(IProductoService iProductoService) {

        this.iProductoService = iProductoService;
    }

    @Operation(summary = "Registro de un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto guardado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/registrar")
    public ResponseEntity<ProductoSalidaDto> registrarProducto(@Valid @RequestBody ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException, DuplicateEntryException {
        return new ResponseEntity<>(iProductoService.registrarProducto(productoEntradaDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Modificación de un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "UServer error",
                    content = @Content)
    })
    @PutMapping("/modificar")
    public ResponseEntity<ProductoSalidaDto> modificarProducto(@Valid @RequestBody ProductoModificacionEntradaDto productoModificacionEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.modificarProducto(productoModificacionEntradaDto), HttpStatus.OK);
    }

    @Operation(summary = "Listado de todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarTodosLosProductos() {
        return new ResponseEntity<>(iProductoService.listarProductos(), HttpStatus.OK);
    }

    @Operation(summary = "Eliminación de un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        iProductoService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Búsqueda de un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "ID inválido",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @GetMapping("/buscarPorId/{id}")
    public ResponseEntity<ProductoSalidaDto> buscarProductoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.buscarProductoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Listado de todos los productos disponibles para reservar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/buscarProductoDisponible")
    public ResponseEntity<List<ProductoSalidaDto>> buscarProductoDisponible(@Valid @RequestBody ProductoDisponibleEntradaDto productoDisponibleEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.buscarProductoDisponible(productoDisponibleEntradaDto), HttpStatus.OK);
    }

    @Operation(summary = "Listado de todos los productos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos por categoria obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/listarProductosPorCategoria")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductoPorCategoria(@Valid @RequestBody ProductoPorCategoria productoPorCategoria) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.listarProductoPorCategoria(productoPorCategoria),HttpStatus.OK);
    }
}
