package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.favorito.FavoritoEntrada;
import com.ebikerrent.alquilerbicicletas.dto.entrada.favorito.FavoritoEntradaLista;
import com.ebikerrent.alquilerbicicletas.dto.salida.favorito.FavoritoSalida;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IFavoritoService;
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
@AllArgsConstructor
@RequestMapping("/productoFavorito")
public class FavoritoController {
    private final IFavoritoService iFavoritoService;

    @Operation(summary = "Se agrega un producto a favoritos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto agregado a favoritos correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = FavoritoSalida.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/agregar")
    public ResponseEntity<FavoritoSalida> agregarProductoFavorito(@Valid @RequestBody FavoritoEntrada productoFavoritoEntrada) throws ResourceNotFoundException {
        return new ResponseEntity<>(iFavoritoService.agregarProductoFavorito(productoFavoritoEntrada), HttpStatus.CREATED);
    }

    @Operation(summary = "Listado de todos los productos favoritos por usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos favoritos por usuario obtenido correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoSalidaDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error",
                    content = @Content)
    })
    @PostMapping("/listarFavoritosPorUsuario")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductosFavoritosPorUsuario(@Valid @RequestBody FavoritoEntradaLista favoritoEntradaLista) throws ResourceNotFoundException {
        return new ResponseEntity<>(iFavoritoService.listarProductosFavoritosPorUsuario(favoritoEntradaLista),HttpStatus.OK);
    }
}
