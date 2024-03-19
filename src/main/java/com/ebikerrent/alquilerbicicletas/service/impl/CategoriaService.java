package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CategoriaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.categoria.CategoriaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.CategoriaRepository;
import com.ebikerrent.alquilerbicicletas.service.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService implements ICategoriaService {

    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaService.class);
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, ModelMapper modelMapper) {
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto categoriaEntradaDto) throws ResourceNotFoundException {

        if (categoriaRepository.findByTitulo(categoriaEntradaDto.getTitulo()) != null) {
            LOGGER.info("Ya existe una categoria con el mismo nombre" + categoriaRepository.findByTitulo(categoriaEntradaDto.getTitulo()));
            throw new ResourceNotFoundException("Existe una categoria con el mismo nombre");
        }

        Categoria categoriaRecibida = dtoEntradaAentidad(categoriaEntradaDto);
        Categoria categoriaRegistrada = categoriaRepository.save(categoriaRecibida);
        CategoriaSalidaDto categoriaResultado = entidadAdtoSalida(categoriaRegistrada);
        LOGGER.info("Categoria registrada: " + categoriaRegistrada);
        return categoriaResultado;
    }
    @Override
    public List<CategoriaSalidaDto> listarCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        List<CategoriaSalidaDto> categoriaSalidaDtoList = new ArrayList<>();

        for (Categoria cat: categorias){
            CategoriaSalidaDto categoriaSalidaDto = entidadAdtoSalida(cat);
            categoriaSalidaDtoList.add(categoriaSalidaDto);
        }

        LOGGER.info("Listado de las categorias : " + categorias);
        return categoriaSalidaDtoList;
    }

    @Override
    public CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException {
        Categoria categoriaBuscada = categoriaRepository.findById(id).orElse(null);

        CategoriaSalidaDto categoriaEncontrada = null;
        if (categoriaBuscada !=null){
            categoriaEncontrada = entidadAdtoSalida(categoriaBuscada);
            LOGGER.info("Producto encontrado : " + categoriaBuscada);
        } else {
            LOGGER.error("El id de la Categoría no se encuentra en la base de datos");
            throw new ResourceNotFoundException("No se encontró la categoria en la base de datos");
        }

        return categoriaEncontrada;
    }

    @Override
    public void eliminarCategoria(Long id) throws ResourceNotFoundException {
        Categoria categoriaBuscada = categoriaRepository.findById(id).orElse(null);

        if (categoriaBuscada !=null){
            categoriaRepository.deleteById(id);
            LOGGER.warn("Se eliminó la categoria con id: " + categoriaBuscada);
        } else
            throw new ResourceNotFoundException("No se encontró la categoría en la base de datos");
            LOGGER.error("No se encontró la categoría en la base de datos");

    }

    @Override
    public CategoriaSalidaDto modificarCategoria(CategoriaModificacionEntradaDto categoriaModificacionEntradaDto) throws ResourceNotFoundException {
        Categoria categoriaEncontrada = categoriaRepository.findById(categoriaModificacionEntradaDto.getId()).orElse(null);

        CategoriaSalidaDto categoriaGuardada = null;
        if (categoriaEncontrada !=null){
            Categoria categoriaModificada = dtoModificadoAentidad(categoriaModificacionEntradaDto);
            categoriaRepository.save(categoriaModificada);
            categoriaGuardada = entidadAdtoSalida(categoriaModificada);
        }else {
            LOGGER.error("No se encontró la Categoría ");
            throw new ResourceNotFoundException("No se encontró la categoría con el id:" + categoriaEncontrada);
        }
        return categoriaGuardada;
    }

    public Categoria dtoEntradaAentidad(CategoriaEntradaDto categoriaEntradaDto){
        return modelMapper.map(categoriaEntradaDto, Categoria.class);
    }

    public CategoriaSalidaDto entidadAdtoSalida(Categoria categoria){
        return modelMapper.map(categoria, CategoriaSalidaDto.class);
    }

    public Categoria dtoModificadoAentidad(CategoriaModificacionEntradaDto categoriaModificacionEntradaDto){
        return modelMapper.map(categoriaModificacionEntradaDto, Categoria.class);
    }

}
