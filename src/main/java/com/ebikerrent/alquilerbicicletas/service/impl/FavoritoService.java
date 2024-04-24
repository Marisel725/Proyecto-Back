package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.favorito.FavoritoEntrada;
import com.ebikerrent.alquilerbicicletas.dto.entrada.favorito.FavoritoEntradaLista;
import com.ebikerrent.alquilerbicicletas.dto.salida.favorito.FavoritoSalida;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.usuario.UsuarioSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Favorito;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.entity.Usuario;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.FavoritoRepository;
import com.ebikerrent.alquilerbicicletas.repository.ProductoRepository;
import com.ebikerrent.alquilerbicicletas.repository.UsuarioRepository;
import com.ebikerrent.alquilerbicicletas.service.IFavoritoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritoService implements IFavoritoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final FavoritoRepository favoritoRepository;
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final ModelMapper modelMapper;

    public FavoritoService(ProductoRepository productoRepository, UsuarioRepository usuarioRepository, FavoritoRepository favoritoRepository, ProductoService productoService, UsuarioService usuarioService, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
        this.favoritoRepository = favoritoRepository;
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.modelMapper = modelMapper;
        configuracionMapper();
    }

    @Override
    public FavoritoSalida agregarProductoFavorito(FavoritoEntrada favoritoEntrada) throws ResourceNotFoundException {
        Long productoId = favoritoEntrada.getProducto_id();
        String usuarioCorreo = favoritoEntrada.getCorreo();
        Boolean favorito = favoritoEntrada.getFavorito();

        Producto producto = productoRepository.findById(productoId).orElse(null);
        Usuario usuario= usuarioRepository.findByMail(usuarioCorreo);

        if (!favorito) {
            LOGGER.info("Debe indicar el producto en true para agregarlo a favoritos");
            throw new ResourceNotFoundException("Debe indicar el producto en true para agregarlo a favoritos");
        }
        if (producto == null) {
            LOGGER.info("No existe un producto con ID: " + productoId);
            throw new ResourceNotFoundException("No existe un producto con ID: " + productoId);
        }
        if (usuario == null) {
            LOGGER.info("No existe un usuario con correo: " + usuarioCorreo);
            throw new ResourceNotFoundException("No existe un usuario con correo: " + usuarioCorreo);
        }
        FavoritoSalida favoritoSalida;
        Favorito buscarFavorito = favoritoRepository.findByProductoAndUsuario(producto, usuario);
        if(buscarFavorito != null) {
            LOGGER.info("El producto con ID: " + productoId + " ya se encuentra en su lista de favoritos");
            throw new ResourceNotFoundException("El producto con ID: " + productoId + " ya se encuentra en su lista de favoritos");
        } else {
            Favorito favoritoEntidad = dtoEntradaAentidad(favoritoEntrada);
            favoritoEntidad.setProducto(producto);
            favoritoEntidad.setUsuario(usuario);
            favoritoEntidad.getFavorito();

            favoritoRepository.save(favoritoEntidad);
            favoritoSalida = entidadAdtoSalida(favoritoEntidad);

            favoritoSalida.setProducto(entidadProductoAdtoSalida(producto));
            favoritoSalida.setUsuario(entidadUsuarioAdtoSalida(usuario));
        }
        return favoritoSalida;
    }

    @Override
    public List<FavoritoSalida> listarProductosFavoritos() {
        return null;
    }

    @Override
    public List<ProductoSalidaDto> listarProductosFavoritosPorUsuario(FavoritoEntradaLista favoritoEntrada) throws ResourceNotFoundException {
        String correoUsuario = favoritoEntrada.getCorreo();
        Usuario usuario = usuarioRepository.findByMail(correoUsuario);
        if (usuario == null) {
            LOGGER.info("No existe un usuario con correo: " + correoUsuario);
            throw new ResourceNotFoundException("No existe un usuario con correo: " + correoUsuario);
        }
        List<Favorito> favoritos = favoritoRepository.findByUsuario(usuario);
        List<ProductoSalidaDto> productosFavoritos = new ArrayList<>();
        if(!favoritos.isEmpty()){
            for (Favorito favorito : favoritos) {
                FavoritoSalida favoritoSalida = entidadAdtoSalida(favorito);
                productosFavoritos.add(favoritoSalida.getProducto());
            }
        } else {
            LOGGER.info("No tiene productos en la lista de favoritos");
            throw new ResourceNotFoundException("No tiene productos en la lista de favoritos");
        }
        return productosFavoritos;
    }

    @Override
    public void eliminarFavorito() {

    }
    private void configuracionMapper() {

        modelMapper.typeMap(ProductoSalidaDto.class, FavoritoSalida.class)
                .addMappings(mapper -> mapper.map(src -> src, FavoritoSalida::setProducto));
    }

    private Favorito dtoEntradaAentidad(FavoritoEntrada favoritoEntrada) {
        return modelMapper.map(favoritoEntrada, Favorito.class);
    }

    private FavoritoSalida entidadAdtoSalida(Favorito favorito){
        FavoritoSalida favoritoSalida = modelMapper.map(favorito, FavoritoSalida.class);
        //favoritoSalida.setProducto(entidadProductoAdtoSalida(favorito.getProducto()));
        //favoritoSalida.setUsuario(entidadUsuarioAdtoSalida(favorito.getUsuario()));
        return favoritoSalida;
    }

    private ProductoSalidaDto entidadProductoAdtoSalida(Producto producto) {
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }

    private UsuarioSalidaDto entidadUsuarioAdtoSalida(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioSalidaDto.class);
    }


}
