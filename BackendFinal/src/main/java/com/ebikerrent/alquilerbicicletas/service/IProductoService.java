package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ProductoModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoDisponibleEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoPorCategoria;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;


import java.util.List;

public interface IProductoService {
    List<ProductoSalidaDto> listarProductos();
    ProductoSalidaDto registrarProducto(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException, DuplicateEntryException;
    ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException;
    void eliminarProducto(Long id) throws ResourceNotFoundException;
    ProductoSalidaDto modificarProducto (ProductoModificacionEntradaDto productoModificacionEntradaDto) throws ResourceNotFoundException;
    List<ProductoSalidaDto> listarProductoPorCategoria(ProductoPorCategoria productoPorCategoria) throws ResourceNotFoundException;
   ProductoSalidaDto buscarProductoPorNombre(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException;
    List<ProductoSalidaDto> buscarProductoDisponible(ProductoDisponibleEntradaDto productoDisponibleEntradaDto) throws ResourceNotFoundException;
}
