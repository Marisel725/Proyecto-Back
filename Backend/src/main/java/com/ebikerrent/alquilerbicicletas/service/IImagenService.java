package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ImagenModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImagenEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.imagen.ImagenSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IImagenService {
    List<ImagenSalidaDto> listarImagenes();
    List<ImagenSalidaDto> listarImagenesPorProducto(Long id);
    ImagenSalidaDto registrarImagen(ImagenEntradaDto imagenEntradaDto) throws ResourceNotFoundException;
    ImagenSalidaDto buscarImagenPorId(Long id);
    void eliminarImagen(Long id) throws ResourceNotFoundException;
    ImagenSalidaDto modificarImagen(ImagenModificacionEntradaDto imagenModificacionEntradaDto) throws ResourceNotFoundException;
}
