package com.ebikerrent.alquilerbicicletas.service;

import com.ebikerrent.alquilerbicicletas.dto.entrada.favorito.FavoritoEntrada;
import com.ebikerrent.alquilerbicicletas.dto.entrada.favorito.FavoritoEntradaLista;
import com.ebikerrent.alquilerbicicletas.dto.salida.favorito.FavoritoSalida;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;

import java.util.List;


public interface IFavoritoService {
    FavoritoSalida agregarProductoFavorito(FavoritoEntrada productoFavoritoEntrada) throws ResourceNotFoundException;
    List<FavoritoSalida> listarProductosFavoritos();
    List<ProductoSalidaDto> listarProductosFavoritosPorUsuario(FavoritoEntradaLista favoritoEntradaLista) throws ResourceNotFoundException;
    void eliminarFavorito();
}
