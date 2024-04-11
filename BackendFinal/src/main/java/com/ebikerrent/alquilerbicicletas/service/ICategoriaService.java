package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.CategoriaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.categoria.CategoriaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.BadRequestException;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

public interface ICategoriaService {
    List<CategoriaSalidaDto> listarCategorias();
    CategoriaSalidaDto registrarCategoria(CategoriaEntradaDto categoriaEntradaDto) throws DuplicateEntryException;

    CategoriaSalidaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException;


    void eliminarCategoria(Long id) throws ResourceNotFoundException, DataIntegrityViolationException;

    CategoriaSalidaDto modificarCategoria (CategoriaModificacionEntradaDto categoriaModificacionEntradaDto) throws ResourceNotFoundException;

}
