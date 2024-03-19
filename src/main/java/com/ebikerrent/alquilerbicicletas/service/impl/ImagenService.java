package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImgenEntradaDtoProducto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ImagenModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImagenEntradaDto;
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


    private final ModelMapper modelMapper;

    public ImagenService(ImagenRepository imagenRepository, ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.imagenRepository = imagenRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        configuracionMapper();
    }

    @Override
    public List<ImagenSalidaDto> listarImagenes() {
        List<Imagen> imagenes = imagenRepository.findAll();
        List<ImagenSalidaDto> imagenSalidaDtoList= new ArrayList<>();

        for (Imagen img: imagenes){

            ImagenSalidaDto imagenSalidaDto = entidadAdtoSalida(img);
            imagenSalidaDtoList.add(imagenSalidaDto);
        }
        LOGGER.info("Listado de todos las imagenes : " + imagenes);

        return imagenSalidaDtoList;
    }

    @Override
    public List<ImagenSalidaDto> listarImagenesPorProducto(Long id) {
        Producto productoEncontrado= productoRepository.findById(id).orElse(null);//te devuelve un objeto de la clase Producto (id, nombre, descripicion,etc)
        Long producto_id = productoEncontrado.getId();


        List<Imagen> imagenes = imagenRepository.findAllByProductoId(producto_id);

        List<ImagenSalidaDto> imagenSalidaDtoList= new ArrayList<>();

        for (Imagen i : imagenes){
            ImagenSalidaDto imagenSalidaDto = entidadAdtoSalida(i);
            imagenSalidaDtoList.add(imagenSalidaDto);
        }


        return imagenSalidaDtoList;
    }

    @Override
    public ImagenSalidaDto registrarImagen(ImagenEntradaDto imagenEntradaDto) throws ResourceNotFoundException {

        Producto productoBuscado = productoRepository.findById(imagenEntradaDto.getProducto_id()).orElse(null);
        if (productoBuscado == null){
            LOGGER.info("No existe el producto con el id "+ imagenEntradaDto.getProducto_id());
            throw new ResourceNotFoundException("No existe el producto");
        }

        Imagen imagenRecibida = dtoEntradaAentidad(imagenEntradaDto);

        imagenRecibida.setProducto(productoBuscado);



        Imagen imagenRegistrada = imagenRepository.save((imagenRecibida));

        ImagenSalidaDto imagenResultado = entidadAdtoSalida(imagenRegistrada);

        //imagenResultado.getNombreProducto();
        //imagenResultado.setNombreProducto(imagenResultado.getNombreProducto());

        LOGGER.info("IMAGEN REGISTRADA: " + imagenRegistrada);

        return imagenResultado;
    }

    @Override
    public ImagenSalidaDto buscarImagenPorId(Long id) {
        Imagen imagenBuscado = imagenRepository.findById(id).orElse(null);

        ImagenSalidaDto imagenEncontrado = null;
        if (imagenBuscado != null){
            imagenEncontrado = entidadAdtoSalida(imagenBuscado);
            LOGGER.info("Imagen encontrado : " + imagenBuscado);
        }else
            LOGGER.error("El id de la imagen no se encuentra en la base de datos");

        return imagenEncontrado;
    }

    @Override
    public void eliminarImagen(Long id) throws ResourceNotFoundException {
       Imagen imagenBuscada = imagenRepository.findById(id).orElse(null);

       if (imagenBuscada  != null) {
           imagenRepository.deleteById(id);
           LOGGER.warn("Se eliminó la imagen con id: " + imagenBuscada);
       }else
           throw new ResourceNotFoundException("No se encontró la imagen en la base de datos:" + imagenBuscada);
       LOGGER.error("No se encontró la imagen en la base de datos" );
    }

    @Override
    public ImagenSalidaDto modificarImagen(ImagenModificacionEntradaDto imagenModificacionEntradaDto) throws ResourceNotFoundException {
        Imagen imagenPorID = imagenRepository.findById(imagenModificacionEntradaDto.getId()).orElse(null);

        ImagenSalidaDto imagenSalidaDtoModificado = null;
        if (imagenPorID != null) {
            imagenPorID.setTitulo(imagenModificacionEntradaDto.getTitulo());
            imagenPorID.setUrlImg(imagenModificacionEntradaDto.getUrlImg());
            Imagen imagenModificadaAguardar = imagenRepository.save(imagenPorID);
            imagenSalidaDtoModificado= entidadAdtoSalida(imagenModificadaAguardar);
            LOGGER.info("Imagen Modificado : " + imagenPorID);
        } else {
            LOGGER.error("La imagen no se encontró:" + imagenModificacionEntradaDto.getId());
            throw new ResourceNotFoundException("No existe la Imagen con id:" + imagenModificacionEntradaDto.getId());

        }
        return imagenSalidaDtoModificado;
    }






    public Imagen dtoEntradaAentidad(ImagenEntradaDto imagenEntradaDto){
        return modelMapper.map(imagenEntradaDto, Imagen.class);
    }

    public ImagenSalidaDto dtoEntradaImagenProductoAentidad (Imagen imgenEntradaDtoProducto){
        return modelMapper.map(imgenEntradaDtoProducto, ImagenSalidaDto.class);
    }

    public ImagenSalidaDto entidadAdtoSalida(Imagen imagen){

        return modelMapper.map(imagen, ImagenSalidaDto.class);
    }
    public Imagen dtoSalidaAentidad (ImagenSalidaDto imagenSalidaDto){
        return modelMapper.map(imagenSalidaDto, Imagen.class);
    }


    private void configuracionMapper(){
        modelMapper.typeMap(Producto.class, ImagenSalidaDto.class)
                .addMappings(mapper ->
                {
                    mapper.map(Producto::getNombre,ImagenSalidaDto::setNombreProducto);

                });


    }




}
