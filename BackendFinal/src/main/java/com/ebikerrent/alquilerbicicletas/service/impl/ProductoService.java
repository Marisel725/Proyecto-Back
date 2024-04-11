package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ProductoModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoDisponibleEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoPorCategoria;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Caracteristica;
import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.exceptions.DuplicateEntryException;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.CaracteristicaRepository;
import com.ebikerrent.alquilerbicicletas.repository.CategoriaRepository;
import com.ebikerrent.alquilerbicicletas.repository.ProductoRepository;
import com.ebikerrent.alquilerbicicletas.service.IProductoService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ProductoService implements IProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;
    private final CategoriaRepository categoriaRepository;
    private final CaracteristicaRepository caracteristicaRepository;

    public ProductoService(ProductoRepository productoRepository, ModelMapper modelMapper, CategoriaRepository categoriaRepository, CaracteristicaRepository caracteristicaRepository) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        configuracionMapper();
    }

    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException, DuplicateEntryException {

        if (productoRepository.findByNombre(productoEntradaDto.getNombre()) != null) {
            LOGGER.info("Ya existe un producto con el mismo nombre ", productoEntradaDto.getNombre());
            throw new DuplicateEntryException("Existe un producto con el mismo nombre");
        }

        String categoriaId = productoEntradaDto.getCategoriaString();
        LOGGER.info("ESTÁ ENTARNDO" + productoEntradaDto.getCategoriaString());
        Categoria categoria = categoriaRepository.findByTitulo(categoriaId);
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaId);
        }

        Set<Caracteristica> caracteristicasList = new HashSet<>();
        Set<String> arrayDeCaracteristicas = productoEntradaDto.getCaracteristica_nombre();
        for (String caracteristica : arrayDeCaracteristicas) {
            Caracteristica caracteristicaBuscada = caracteristicaRepository.findByNombre(caracteristica);
            if (caracteristicaBuscada == null) {
                LOGGER.error("No se encontró la caracteristica buscada");
                throw new ResourceNotFoundException("No se encontró la caracteristica en la base de datos: " + caracteristica);
            }
            caracteristicasList.add(caracteristicaBuscada);
        }

        Producto productRecibido = dtoEntradaAentidad(productoEntradaDto);
        productRecibido.setCategoria(categoria);
        productRecibido.setCaracteristicas(caracteristicasList);

        Producto productoRegistrado = productoRepository.save((productRecibido));
        ProductoSalidaDto productoResultado = entidadAdtoSalida(productoRegistrado);
        LOGGER.info("PRODUCTO REGISTRADO : " + productoRegistrado);
        LOGGER.info("PRODUCTO SALIDA DTO : " + productoResultado);
        return productoResultado;
    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(id).orElse(null);

        ProductoSalidaDto productoEncontrado = null;
        if (productoBuscado != null) {
            productoEncontrado = entidadAdtoSalida(productoBuscado);
            LOGGER.info("Producto encontrado : " + productoBuscado);
        } else {
            LOGGER.error("El id del producto no se encuentra en la base de datos");
            throw new ResourceNotFoundException("En la base de datos no se encontro el producto con ID: " + id);
        }
        return productoEncontrado;
    }

    @Override
    public List<ProductoSalidaDto> listarProductos() {

        List<Producto> productos = productoRepository.findAll();

        Collections.shuffle(productos);
        int contador = 0;
        List<ProductoSalidaDto> productoSalidaDtoList = new ArrayList<>();

        for (Producto p : productos) {
            if (contador >= 10){
                break;
            }
            ProductoSalidaDto productoSalidaDto = entidadAdtoSalida(p);
            productoSalidaDtoList.add(productoSalidaDto);
            contador++;
        }
        LOGGER.info("Listado de todos los productos : " + productos);
        return productoSalidaDtoList;
    }

    @Override
    public void eliminarProducto(Long id) throws ResourceNotFoundException {

        Optional<Producto> buscarProducto = productoRepository.findById(id);

        if (buscarProducto != null) {
            LOGGER.warn("Se eliminó el producto con el id : " + dtoSalidaAentidad(buscarProductoPorId(id)));
            productoRepository.deleteById(id);
        } else {
            LOGGER.error("No se encontró el producto con el id : " + id);
            throw new ResourceNotFoundException("No se encontró el producto con el id : " + id);
        }
    }

    @Override
    public ProductoSalidaDto modificarProducto(ProductoModificacionEntradaDto productoModificacionEntradaDto) throws ResourceNotFoundException {

        LOGGER.info("PRODUCTO A MODIFICAR: " + productoModificacionEntradaDto);  //entra sin imags
        Long buscarProductoId = productoModificacionEntradaDto.getId();
        Optional<Producto> productoBuscado = productoRepository.findById(buscarProductoId);
        if (!productoBuscado.isPresent()) {
            throw new ResourceNotFoundException("No se encontró el producto con el ID proporcionado: " + buscarProductoId);
        }
        LOGGER.info("PRODUCTO: " + productoBuscado);

        String categoriaTitulo = productoModificacionEntradaDto.getTituloCategoria();
        Categoria categoria = categoriaRepository.findByTitulo(categoriaTitulo);
        if (categoria == null) {
            throw new ResourceNotFoundException("No se encontró la categoría con el nombre proporcionado: " + categoriaTitulo);
        }

        Set<Caracteristica> caracteristicasList = new HashSet<>();
        Set<String> arrayDeCaracteristicas = productoModificacionEntradaDto.getCaracteristicas();
        for (String caracteristica : arrayDeCaracteristicas) {
            Caracteristica caracteristicaBuscada = caracteristicaRepository.findByNombre(caracteristica);
            if (caracteristicaBuscada == null) {
                LOGGER.error("No se encontró la caracteristica buscada");
                throw new ResourceNotFoundException("No se encontró la caracteristica en la base de datos: " + caracteristica);
            }
            caracteristicasList.add(caracteristicaBuscada);
        }
        ProductoSalidaDto productoSalidaDto = null;

        if (productoBuscado != null) {
            LOGGER.info("PRODUCTO DENTRO DEL IF: " + productoBuscado);
            Producto productoMap = dtoModificacioAentidad(productoModificacionEntradaDto);
            productoMap.setCategoria(categoria);
            productoMap.setCaracteristicas(caracteristicasList);
            LOGGER.info("PRODUCTO MAPEADO: " + productoMap);
            productoMap.setImagenes(productoBuscado.get().getImagenes());
            LOGGER.info("PRODUCTO SETEADO: " + productoMap);

            Producto guardarProducto = productoRepository.save(productoMap);
            LOGGER.info("PRODUCTO GUARDADO: " + guardarProducto);

            productoSalidaDto = entidadAdtoSalida(guardarProducto);
            LOGGER.info("PRODUCTO SALIDA: " + productoSalidaDto);

            LOGGER.info("El producto " + productoMap + " fue modificado exitosamente ");
        } else {
            LOGGER.info("El producto " + buscarProductoId + " no fue encontrado.");
            throw new ResourceNotFoundException("El producto: " + productoBuscado + "  no fue encontrado.");
        }
        return productoSalidaDto;
    }

    @Override
    public List<ProductoSalidaDto> listarProductoPorCategoria(ProductoPorCategoria productoPorCategoria) throws ResourceNotFoundException {
        String categoria = productoPorCategoria.getNombreCategoria();
        Categoria categoriaBuscada = categoriaRepository.findByTitulo(categoria);
        List<ProductoSalidaDto> productoSalidaDtoList = new ArrayList<>();

        if (categoriaBuscada == null) {
            LOGGER.info("La categoria con nombre: " + categoria + " no existe.");
            throw new ResourceNotFoundException("La categoria con nombre: " + categoria + " no existe.");
        } else {
            List<Producto> productosBuscados = productoRepository.findAllByCategoria(categoriaBuscada);
            for (Producto producto : productosBuscados) {
                    productoSalidaDtoList.add(entidadAdtoSalida(producto));
                    Collections.shuffle(productoSalidaDtoList);
                    LOGGER.info("Productos por categoria: " + productoSalidaDtoList);
            }
        }
        return productoSalidaDtoList;
    }

    @Override
    public ProductoSalidaDto buscarProductoPorNombre(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {
        String nombreProducto = productoEntradaDto.getNombre();
        Producto productoPorNombre = productoRepository.findByNombre(nombreProducto);

        ProductoSalidaDto productoEncontrado = null;
        if (productoPorNombre != null) {
            productoEncontrado = entidadAdtoSalida(productoPorNombre);
            LOGGER.info("Producto encontrado por nombre : " + productoPorNombre);
        } else {
            LOGGER.info("No se encontró el producto con el nombre : " + nombreProducto);
            throw new ResourceNotFoundException("No se encontró el producto con el nombre : " + nombreProducto);
        }
        return productoEncontrado;
    }


    @Override
    public List<ProductoSalidaDto> buscarProductoDisponible(ProductoDisponibleEntradaDto productoDisponibleEntradaDto) throws ResourceNotFoundException {
        String nombreProducto = productoDisponibleEntradaDto.getNombreProducto();
        LocalDate fechaInicio = productoDisponibleEntradaDto.getFechaInicio();
        LocalDate fechaFin = productoDisponibleEntradaDto.getFechaFin();

        List<Producto> productosBuscados;
        List<ProductoSalidaDto> productosDisponibles = new ArrayList<>();

        if (nombreProducto == null || nombreProducto.trim().isEmpty()) {
            productosBuscados = productoRepository.findAll();
        } else {
            productosBuscados = productoRepository.findAllByNombreContaining(nombreProducto.trim());
        }

        for (Producto producto : productosBuscados) {
            if (ChronoUnit.DAYS.between(fechaInicio, fechaFin) < 2) {
                LOGGER.error("La fecha de reserva debe ser mayor a 48hs");
                throw new ResourceNotFoundException("La fecha de reserva debe ser mayor a 48hs");
            } else if (buscadorProductoPorFecha(producto, fechaInicio, fechaFin)) {
                productosDisponibles.add(entidadAdtoSalida(producto));
            } else {
                LOGGER.info("El producto " + producto.getNombre() + " no se encuentra disponible en esa fecha");
            }
        }
        return productosDisponibles;
    }


    private boolean buscadorProductoPorFecha(Producto producto, LocalDate fechaInicio, LocalDate fechaFin) {
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            if (producto.getFechasReservadas().contains(fecha)) {
                LOGGER.info("La fecha: " + fecha + " hasta la fecha: " + fechaFin + " ya se encuentra reservada");
                return false; // El producto no está disponible para las fechas buscadas
            }
        }
        return true;
    }


    private void configuracionMapper() {
        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
                .addMappings(mapper ->
                {
                    mapper.map(ProductoEntradaDto::getImagenEntradaDtoProductos, Producto::setImagenes);
                    //mapper.map(ProductoEntradaDto:: getCategoriaEntradaDto,Producto::setCategoria);
                });
        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper ->
                {
                    mapper.map(Producto::getImagenes, ProductoSalidaDto::setImagenSalidaDtoProductos);
                    mapper.map(Producto::getCategoria, ProductoSalidaDto::setCategoriaSalidaDto);
                    mapper.map(Producto::getCaracteristicas, ProductoSalidaDto::setCaracteristicaSalidaDto);
                });

    }

    public Producto dtoEntradaAentidad(ProductoEntradaDto productoEntradaDto) {
        return modelMapper.map(productoEntradaDto, Producto.class);
    }

    public ProductoSalidaDto entidadAdtoSalida(Producto producto) {
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }

    public Producto dtoSalidaAentidad(ProductoSalidaDto productoSalidaDto) {
        return modelMapper.map(productoSalidaDto, Producto.class);
    }

    public Producto dtoModificacioAentidad(ProductoModificacionEntradaDto productoModificacionEntradaDto) {

        return modelMapper.map(productoModificacionEntradaDto, Producto.class);
    }

    public CategoriaSalidaDto entidadCatDtoSalida(Categoria categoria) {
        return modelMapper.map(categoria, CategoriaSalidaDto.class);
    }
}
