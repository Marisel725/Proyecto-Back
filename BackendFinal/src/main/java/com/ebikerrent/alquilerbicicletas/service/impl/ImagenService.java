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
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImagenService implements IImagenService {
    private final Logger LOGGER = LoggerFactory.getLogger(ImagenService.class);
    private final ImagenRepository imagenRepository;
    private final ProductoRepository productoRepository;
    private final ProductoService productoService;
    private final ModelMapper modelMapper;

    public ImagenService(ImagenRepository imagenRepository, ProductoRepository productoRepository, ProductoService productoService, ModelMapper modelMapper) {
        this.imagenRepository = imagenRepository;
        this.productoRepository = productoRepository;
        this.productoService = productoService;
        this.modelMapper = modelMapper;
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
    public ImagenSalidaDto buscarImagenPorId(Long id) throws ResourceNotFoundException {
        Imagen imagenBuscado = imagenRepository.findById(id).orElse(null);

        ImagenSalidaDto imagenEncontrado = null;
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
        Imagen imagenAmodificar = dtoModificacioAentidad(imagenModificacionEntradaDto);
        Imagen imagenPorID = imagenRepository.findById(imagenAmodificar.getId()).orElse(null);

        ImagenSalidaDto imagenSalidaDtoModificado = null;
        if (imagenPorID != null) {
            Imagen imagenModificado = imagenRepository.save(imagenAmodificar);
            imagenSalidaDtoModificado = entidadAdtoSalida((imagenModificado));
            LOGGER.info("Imagen Modificado : " + imagenModificado);
        } else
            LOGGER.error("La imagen no se encontró");


        return imagenSalidaDtoModificado;
    }

    /*@Override
    public ImagenSalidaDto modificarImagen(ImagenModificacionEntradaDto imagenModificacionEntradaDto) throws ResourceNotFoundException {

        if(!imagenRepository.findById(imagenModificacionEntradaDto.getId()).isPresent()){
            LOGGER.info("No existe la imagen con id: " + imagenModificacionEntradaDto.getId());
            throw new ResourceNotFoundException("No se encontro imagen con id: " + imagenModificacionEntradaDto.getId());
        }

        List<ProductoSalidaDto> listaProductos = productoService.listarProductos();
        LOGGER.info("LISTA PRODUCTOS: " + listaProductos);
        Producto productoAsociado = null;
        for (ProductoSalidaDto productoSalida : listaProductos){
            Producto producto = productoRepository.findById(productoSalida.getId()).orElse(null);
            if(producto.getImagenes().stream().anyMatch(imagen -> imagen.getId().equals(imagenModificacionEntradaDto.getId()))) {
                productoAsociado = producto;
                LOGGER.info("PRODUCTO ASOCIADO: " + productoAsociado);
                break;
            }
        }
        LOGGER.info("PRODUCTO ASOCIADO FUER IF: " + productoAsociado);

        if (productoAsociado == null) {
            LOGGER.info("No se encontró un producto con ID: " + productoAsociado.getId());
            throw new ResourceNotFoundException("No se encontró un producto con ID: " + productoAsociado.getId());
        }


        Imagen imagenAmodificar = dtoModificacioAentidad(imagenModificacionEntradaDto);
        Imagen imagenModificada = imagenRepository.save(imagenAmodificar);

        Set<Imagen> imagenesProducto = productoAsociado.getImagenes();
        LOGGER.info("IMAGENES PRODUCTO: " + imagenesProducto);
        imagenesProducto.add(imagenModificada);
        LOGGER.info("IMAGEN AGREGADA?: " + imagenesProducto);
        productoAsociado.setImagenes(imagenesProducto);
        LOGGER.info("PRODUCTO ASOCIADO: " + productoAsociado);


        Producto productoGuardado = productoRepository.save(productoAsociado);
        LOGGER.info("Imagen agregada al producto: " + productoGuardado);
        //ProductoSalidaDto productoSalidaDto = entidadAdtoSalida(productoGuardado);
        //LOGGER.info("PRODUCTO SALIDA DTO: " + productoSalidaDto);

        ImagenSalidaDto imagenSalidaDto = entidadAdtoSalida(imagenModificada);
        LOGGER.info("IMAGEN MODIFICACION SALIDA: " + imagenSalidaDto);

        return imagenSalidaDto;
    }*/


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
