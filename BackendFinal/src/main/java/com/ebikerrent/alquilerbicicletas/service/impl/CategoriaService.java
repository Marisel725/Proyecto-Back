package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CategoriaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.categoria.CategoriaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.exceptions.BadRequestException;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.CategoriaRepository;
import com.ebikerrent.alquilerbicicletas.service.ICategoriaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto categoriaEntradaDto) throws DuplicateEntryException {

        if (categoriaRepository.findByTitulo(categoriaEntradaDto.getTitulo()) != null) {
            LOGGER.error("La categoría con ese título ya existe");
            throw new DuplicateEntryException("La categoría con título " + categoriaEntradaDto.getTitulo() + " ya existe");
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

        for (Categoria cat : categorias) {
            CategoriaSalidaDto categoriaSalidaDto = entidadAdtoSalida(cat);
            categoriaSalidaDtoList.add(categoriaSalidaDto);
        }
        LOGGER.info("Listado de las categorias : " + categorias);
        return categoriaSalidaDtoList;
    }

    @Override
    public CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException {
        Categoria categoriaBuscada = categoriaRepository.findById(id).orElse(null);
        CategoriaSalidaDto categoriaSalidaDto;
        if (categoriaBuscada == null) {
            LOGGER.error("El id de esa categoria no se encuentra registrada en la base de datos");
            throw new ResourceNotFoundException("En la base de datos no se encontro la categoria con ID: " + id);
        } else {
            categoriaSalidaDto = entidadAdtoSalida(categoriaBuscada);
            LOGGER.info("Categoria encontrada: {}", categoriaSalidaDto);
        }
        return categoriaSalidaDto;
    }

@Override
public void eliminarCategoria(Long id) throws ResourceNotFoundException, DataIntegrityViolationException {
    Categoria categoria = categoriaRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID: " + id + " no fue encontrada"));

    if (!categoria.getProductos().isEmpty()){
        LOGGER.warn("La categoría con ID {} está asociada a al menos un producto y no puede ser eliminada.", id);
        throw new DataIntegrityViolationException("La categoria no puede ser eliminada porque se encuentra asociada al menos a un producto");
    }
    categoriaRepository.deleteById(id);
    LOGGER.info("Se eliminó la categoría con ID: " + id);
}

    /*
    @Override
    public void eliminarCategoria(Long id) throws ResourceNotFoundException, DataIntegrityViolationException {

        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría con ID: " + id + " no fue encontrada"));

        Set<Producto> productos = categoria.getProductos();

        if (!productos.isEmpty()) {
            throw new DataIntegrityViolationException("La categoría está asociada a al menos un producto y no se puede eliminar");
        }
        categoriaRepository.deleteById(id);
        LOGGER.info("Se eliminó la categoría con ID: " + id);
    }

*/
    @Override
    public CategoriaSalidaDto modificarCategoria(CategoriaModificacionEntradaDto categoriaModificacionEntradaDto) throws ResourceNotFoundException {

        CategoriaSalidaDto categoriaSalidaDto;

        CategoriaSalidaDto buscarCategoria = buscarCategoriaPorId(categoriaModificacionEntradaDto.getId());
        if (buscarCategoria != null) {
            Categoria categoriaMap = dtoModificadoAentidad(categoriaModificacionEntradaDto);
            Categoria categoriaGuardada = categoriaRepository.save(categoriaMap);
            categoriaSalidaDto = entidadAdtoSalida(categoriaGuardada);
            LOGGER.info("La categoria con id : " + categoriaModificacionEntradaDto.getId() + " se ha modificado exitosamente");
        } else {
            LOGGER.info("La categoria con id: " + categoriaModificacionEntradaDto.getId() + " no fue encontrada");
            throw new ResourceNotFoundException("No se logro encontrar la categoria: " + categoriaModificacionEntradaDto);
        }
        return categoriaSalidaDto;
    }

    public Categoria dtoEntradaAentidad(CategoriaEntradaDto categoriaEntradaDto) {
        return modelMapper.map(categoriaEntradaDto, Categoria.class);
    }

    public CategoriaSalidaDto entidadAdtoSalida(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaSalidaDto.class);
    }

    public Categoria dtoSalidaAentidad(CategoriaSalidaDto categoriaSalidaDto) {
        return modelMapper.map(categoriaSalidaDto, Categoria.class);
    }

    public Categoria dtoModificadoAentidad(CategoriaModificacionEntradaDto categoriaModificacionEntradaDto) {
        return modelMapper.map(categoriaModificacionEntradaDto, Categoria.class);
    }

}
