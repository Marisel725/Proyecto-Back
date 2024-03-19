package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ProductoModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IProductoService;
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


    @PostMapping("/registrar")
    public ResponseEntity<ProductoSalidaDto> registrarProducto(@Valid @RequestBody ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.registrarProducto(productoEntradaDto) ,HttpStatus.CREATED);
    }

    @PutMapping("/modificar")
    public ResponseEntity<ProductoSalidaDto>modificarProducto(@Valid @RequestBody ProductoModificacionEntradaDto productoModificacionEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.modificarProducto(productoModificacionEntradaDto), HttpStatus.OK);
    }
    @GetMapping("/listar")
    public ResponseEntity<List<ProductoSalidaDto>> listarTodosLosProductos(){
        return new ResponseEntity<>(iProductoService.listarProductos(), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String>eliminarProducto(@PathVariable Long id ) throws ResourceNotFoundException {
        iProductoService.eliminarProducto(id);
        return new ResponseEntity<>("Producto eliminado correctamente", HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ProductoSalidaDto>buscarProductoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.buscarProductoPorId(id),HttpStatus.OK);
    }

    @GetMapping("/listarProductoPorCategoria/{titulo}")
    public ResponseEntity<List<ProductoSalidaDto>> listarProductoPorCategoria(@PathVariable String titulo) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.listarProductoPorCategoria(titulo), HttpStatus.OK);
    }
    @GetMapping("/buscarProductoPorNombre/{nombre}")
    public ResponseEntity<ProductoSalidaDto>buscarProductoPorNombre(@Valid @RequestBody ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iProductoService.buscarProductoPorNombre(productoEntradaDto), HttpStatus.OK);
    }

}
