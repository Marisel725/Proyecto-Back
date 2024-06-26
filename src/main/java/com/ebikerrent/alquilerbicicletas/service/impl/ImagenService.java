package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImagenEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ImagenModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.imagen.ImagenSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Imagen;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.ImagenRepository;
import com.ebikerrent.alquilerbicicletas.repository.ProductoRepository;
import com.ebikerrent.alquilerbicicletas.service.IImagenService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ImagenService implements IImagenService {
    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);
    private final ImagenRepository imagenRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    @Override
    public ImagenSalidaDto registrarImagen(ImagenEntradaDto imagenEntradaDto) throws ResourceNotFoundException {

        Producto productoBuscado = productoRepository.findById(imagenEntradaDto.getProducto_id()).orElse(null);
        if (productoBuscado == null) {
            LOGGER.info("No existe el producto con el id " + imagenEntradaDto.getProducto_id());
            throw new ResourceNotFoundException("No existe el producto");
        }
        Imagen imagenRecibida = dtoEntradaAentidad(imagenEntradaDto);
        imagenRecibida.setProducto(productoBuscado);

        Imagen imagenRegistrada = imagenRepository.save((imagenRecibida));
        productoBuscado.getImagenes().add(imagenRegistrada);
        ImagenSalidaDto imagenResultado = entidadAdtoSalida(imagenRegistrada);
        LOGGER.info("IMAGEN REGISTRADA: " + imagenRegistrada);
        return imagenResultado;
    }

    @Override
    public List<ImagenSalidaDto> listarImagenes() {
        List<Imagen> imagenes = imagenRepository.findAll();
        List<ImagenSalidaDto> imagenSalidaDtoList = new ArrayList<>();

        for (Imagen img : imagenes) {

            ImagenSalidaDto imagenSalidaDto = entidadAdtoSalida(img);
            imagenSalidaDtoList.add(imagenSalidaDto);
        }
        LOGGER.info("Listado de todos las imagenes : " + imagenes);

        return imagenSalidaDtoList;
    }

    @Override
    public ImagenSalidaDto buscarImagenPorId(Long id) throws ResourceNotFoundException {
        Imagen imagenBuscado = imagenRepository.findById(id).orElse(null);

        ImagenSalidaDto imagenEncontrado;
        if (imagenBuscado != null) {
            imagenEncontrado = entidadAdtoSalida(imagenBuscado);
            LOGGER.info("Imagen encontrado : " + imagenBuscado);
        } else {
            LOGGER.error("El id de la imagen no se encuentra en la base de datos");
            throw new ResourceNotFoundException("En la base de datos no se encontro la imagen con ID: " + id);
        }
        return imagenEncontrado;
    }

    @Override
    public void eliminarImagen(Long id) throws ResourceNotFoundException {
        if (buscarImagenPorId(id) != null) {
            LOGGER.warn("Se eliminó la imagen con el id : " + dtoSalidaAentidad(buscarImagenPorId(id)));
            imagenRepository.deleteById(id);
        } else {
            LOGGER.error("No se encontró la imagen con el id : " + id);
            throw new ResourceNotFoundException("No se ha encontrado la imagen con id " + id);
        }
    }

    @Override
    public ImagenSalidaDto modificarImagen(ImagenModificacionEntradaDto imagenModificacionEntradaDto) throws ResourceNotFoundException {
        Imagen imagenPorID = imagenRepository.findById(imagenModificacionEntradaDto.getId()).orElse(null);

        if (imagenPorID == null) {
            LOGGER.error("No se encuentra la imagen en la BDD");
            throw new ResourceNotFoundException("No se encuentra la imagen en la BDD");
        }

            Producto producto = imagenPorID.getProducto();

            Imagen imagenAmodificar = dtoModificacioAentidad(imagenModificacionEntradaDto);
            imagenAmodificar.setProducto(producto);
            Imagen imagenModificado = imagenRepository.save(imagenAmodificar);
            ImagenSalidaDto imagenSalidaDtoModificado = entidadAdtoSalida((imagenModificado));
            LOGGER.info("Imagen Modificada: " + imagenModificado);

            return imagenSalidaDtoModificado;
    }

    public Imagen dtoEntradaAentidad(ImagenEntradaDto imagenEntradaDto) {
        return modelMapper.map(imagenEntradaDto, Imagen.class);
    }

    public ImagenSalidaDto entidadAdtoSalida(Imagen imagen) {
        return modelMapper.map(imagen, ImagenSalidaDto.class);
    }

    public Imagen dtoSalidaAentidad(ImagenSalidaDto imagenSalidaDto) {
        return modelMapper.map(imagenSalidaDto, Imagen.class);
    }

    public Imagen dtoModificacioAentidad(ImagenModificacionEntradaDto imagenModificacionEntradaDto) {
        return modelMapper.map(imagenModificacionEntradaDto, Imagen.class);
    }
}
