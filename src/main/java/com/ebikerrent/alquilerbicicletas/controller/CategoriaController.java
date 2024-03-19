package com.ebikerrent.alquilerbicicletas.controller;

import com.ebikerrent.alquilerbicicletas.dto.entrada.categoria.CategoriaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CategoriaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.ICategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/categorias")
public class CategoriaController {

    private final ICategoriaService iCategoriaService;

    public CategoriaController(ICategoriaService iCategoriaService) {
        this.iCategoriaService = iCategoriaService;
    }

    @PostMapping("registrar")
    public ResponseEntity<CategoriaSalidaDto> registrarCategoria(@Valid @RequestBody CategoriaEntradaDto categoriaEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iCategoriaService.registrarCategoria(categoriaEntradaDto) , HttpStatus.CREATED);
    }

    @PutMapping("/modificar")
    public ResponseEntity<CategoriaSalidaDto>modificarCategoria(@Valid @RequestBody CategoriaModificacionEntradaDto categoriaModificacionEntradaDto) throws ResourceNotFoundException{
        return new ResponseEntity(iCategoriaService.modificarCategoria(categoriaModificacionEntradaDto), HttpStatus.OK);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<CategoriaSalidaDto>buscarCategoriaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iCategoriaService.buscarCategoriaPorId(id),HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String>eliminarCategoria(@PathVariable Long id ) throws ResourceNotFoundException {
        iCategoriaService.eliminarCategoria(id);
        return new ResponseEntity<>("Categoria eliminado correctamente", HttpStatus.OK);
    }


    @GetMapping("listar")
    public ResponseEntity<List<CategoriaSalidaDto>> listarCategorias(){
        return new ResponseEntity<>(iCategoriaService.listarCategorias(), HttpStatus.OK);
    }

}
