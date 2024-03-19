package com.ebikerrent.alquilerbicicletas.controller;
import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImagenEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ImagenModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ProductoModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.imagen.ImagenSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.service.IImagenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/imagenes")
public class ImagenController {
    private final IImagenService iImagenService;

    public ImagenController(IImagenService iImagenService) {
        this.iImagenService = iImagenService;
    }


    @PostMapping("registrar")
    public ResponseEntity<ImagenSalidaDto> registrarImagen(@Valid @RequestBody ImagenEntradaDto imagenEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iImagenService.registrarImagen(imagenEntradaDto) , HttpStatus.CREATED);
    }

    @GetMapping("listar")
    public ResponseEntity<List<ImagenSalidaDto>> listarImagenes(){
        return new ResponseEntity<>(iImagenService.listarImagenes(), HttpStatus.OK);
    }

    @GetMapping("listarPorProducto/{id}")
    public ResponseEntity<List<ImagenSalidaDto>> listarImagenesPorProducto(@PathVariable Long id){
        return new ResponseEntity<>(iImagenService.listarImagenesPorProducto(id), HttpStatus.OK);
    }

    @PutMapping("/modificar")
    public ResponseEntity<ImagenSalidaDto>modificarImagen(@Valid @RequestBody ImagenModificacionEntradaDto imagenModificacionEntradaDto) throws ResourceNotFoundException {
        return new ResponseEntity<>(iImagenService.modificarImagen(imagenModificacionEntradaDto), HttpStatus.OK);
    }
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ImagenSalidaDto> buscarImagen(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iImagenService.buscarImagenPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String>eliminarImagen(@PathVariable Long id) throws ResourceNotFoundException{
        iImagenService.eliminarImagen(id);
        return new ResponseEntity<>("Imagen eliminada correctamente",HttpStatus.OK);
    }
}
