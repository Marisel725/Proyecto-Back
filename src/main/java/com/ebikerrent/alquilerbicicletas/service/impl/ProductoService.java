package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ProductoModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoDisponibleEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.producto.ProductoEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Caracteristica;
import com.ebikerrent.alquilerbicicletas.entity.Categoria;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductoService implements IProductoService {
    private final Logger LOGGER = LoggerFactory.getLogger(ProductoService.class);
    private final ProductoRepository productoRepository;

    private final CategoriaRepository categoriaRepository;

    private final CaracteristicaRepository caracteristicaRepository;

    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, CaracteristicaRepository caracteristicaRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.caracteristicaRepository = caracteristicaRepository;
        this.modelMapper = modelMapper;
        configuracionMapper();
    }

    @Override
    public ProductoSalidaDto registrarProducto(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {
    //Verifico si existe un producto con el mismo nombre en la base de datos
        if (productoRepository.findByNombre(productoEntradaDto.getNombre()) != null) {
            LOGGER.info("Ya existe un producto con el mismo nombre: " + productoEntradaDto.getNombre());
            throw new ResourceNotFoundException("Existe un producto con el mismo nombre");
        }
    //Busco la categoría correspondiente al título proporcionado en el DTO.
        Categoria categoriaBuscada = categoriaRepository.findByTitulo(productoEntradaDto.getCategoria());

        if (categoriaBuscada == null){
            LOGGER.error("No existe la categoria con el titulo: " + productoEntradaDto.getCategoria());
            throw new ResourceNotFoundException("No existe la categoria con el titulo");
        }

        Set<Caracteristica> caracteristicasList = new HashSet<>();//inicializo una lista

        //Obtengo la lista de nombres desde la Entrada de Producto
        Set<String> arrayDeCaracteristicas = productoEntradaDto.getCaracteristicas_nombre();
        //Iteramos esa lista de nombres
        for (String caracteristica : arrayDeCaracteristicas){
            //Busca la caracteristica en la base de datos por su nombre
            Caracteristica caracteristicaBuscada = caracteristicaRepository.findByNombre(caracteristica);
            if (caracteristicaBuscada == null){ //Si no la encuentra lanza la excepción
                LOGGER.error("No se encontró la caracteristica buscada");
                throw new ResourceNotFoundException("No se encontró la caracteristica en la base de datos: " + caracteristica);
            }
            //Si la encuentra la agrega a la lista previamente creada caracteristicas.list
            caracteristicasList.add(caracteristicaBuscada);
        }
        //Convertimos el ProductoEntraDto a Entidad para poder setearle a Producto Categoría y Características
        Producto productRecibido = dtoEntradaAentidad(productoEntradaDto);
        productRecibido.setCategoria(categoriaBuscada);
        productRecibido.setCaracteristicas(caracteristicasList);
        //Una vez seteados guardamos a la base de datos
        Producto productoRegistrado = productoRepository.save((productRecibido));
        //Convertimos esta Entida Producto a una Dto de Salida
        ProductoSalidaDto productoResultado = entidadAdtoSalida(productoRegistrado);
        LOGGER.info("PRODUCTO REGISTRADO : " + productoRegistrado);
        return productoResultado;
    }

    @Override
    public ProductoSalidaDto buscarProductoPorId(Long id) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(id).orElse(null);

        ProductoSalidaDto productoEncontrado = null;
        if (productoBuscado != null){
            productoEncontrado = entidadAdtoSalida(productoBuscado);
            LOGGER.info("Producto encontrado : " + productoBuscado);
        }else {
            LOGGER.error("El id del producto no se encuentra en la base de datos");
            throw new ResourceNotFoundException("No se encontró el producto en la base de datos");
        }

       return productoEncontrado;
    }

    @Override
    public void eliminarProducto(Long id) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(id).orElse(null);

        if (productoBuscado !=null){
            productoRepository.deleteById(id);
            LOGGER.warn("Se eliminó el producto con id: " + productoBuscado);
        } else
            throw new ResourceNotFoundException("No se encontró el producto en la base de datos");
        LOGGER.error("No se encontró el producto en la base de datos");

    }

    @Override
    public ProductoSalidaDto modificarProducto(ProductoModificacionEntradaDto productoModificacionEntradaDto) throws ResourceNotFoundException {
        Producto productoEncontrado = productoRepository.findByNombre(productoModificacionEntradaDto.getNombre());

        if (productoEncontrado != null && !productoEncontrado.getId().equals(productoModificacionEntradaDto.getId())) {

            LOGGER.info("Ya existe un producto con el mismo nombre");
            throw new ResourceNotFoundException("Existe un producto con el mismo nombre");
        }

        Categoria categoriaBuscada = categoriaRepository.findByTitulo(productoModificacionEntradaDto.getCategoria());

        if (categoriaBuscada == null){
            LOGGER.info("No existe la categoria con el titulo");
            throw new ResourceNotFoundException("No existe la categoria con el titulo");
        }

        Producto productRecibido = dtoModificacioAentidad(productoModificacionEntradaDto);
        productRecibido.setCategoria(categoriaBuscada);

        Producto productoRegistrado = productoRepository.save((productRecibido));
        ProductoSalidaDto productoResultado = entidadAdtoSalida(productoRegistrado);
        LOGGER.info("PRODUCTO REGISTRADO : " + productoRegistrado);
        return productoResultado;
    }

    @Override
    public List<ProductoSalidaDto> listarProductoPorCategoria(String titulo)  {
       List <Producto> productos = productoRepository.findAllByCategoriaTitulo(titulo);
        List<ProductoSalidaDto> productoSalidaDtoList= new ArrayList<>();
        for (Producto p: productos){

            ProductoSalidaDto productoSalidaDto = entidadAdtoSalida(p);
            productoSalidaDtoList.add(productoSalidaDto);
        }
        LOGGER.info("Listado de todos los productos por Categoria : " + productos);

        return productoSalidaDtoList;
    }

    @Override
    public ProductoSalidaDto buscarProductoDisponible(ProductoDisponibleEntradaDto productoDisponibleEntradaDto) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findByNombre(productoDisponibleEntradaDto.getNombreProducto());
        LocalDate fechaInicio = productoDisponibleEntradaDto.getFechaInicio();
        LocalDate fechaFin = productoDisponibleEntradaDto.getFechaFin();

        ProductoSalidaDto productoDisponibleSalidaDto= null;

        List<LocalDate> fechaBuscada= new ArrayList<>();//inicio una lista para completar las fechas buscadas por el usuario

        List<LocalDate>fechasReservadas= productoBuscado.getFechasReservadas();//traigo la lista de fechas reservadas del producto

        if (productoBuscado != null){
            if (fechaFin.compareTo(fechaInicio) >= 2) {
                while (!fechaInicio.isAfter(fechaFin)) {
                    fechaBuscada.add(fechaInicio);
                    fechaInicio = fechaInicio.plusDays(1);
                }
                for (LocalDate fecha : fechaBuscada) {
                    LOGGER.info("Fecha" + fecha);
                    if (fechasReservadas.contains(fecha)) {
                        LOGGER.error("la fecha" + fecha + "se encuentra reseravda");
                        throw new ResourceNotFoundException("La fecha" + fecha + "ya se encuentra reservada");
                    }
                }
                LOGGER.info("El producto se encuentra disponible para reservar en las fechas:" + fechaBuscada);
                productoDisponibleSalidaDto = entidadAdtoSalida(productoBuscado);
            } else {
                LOGGER.error("La fecha de reserva debe ser mayor a 48hs");
                throw  new ResourceNotFoundException("La fecha de reserva debe ser mayor a 48hs");
            }
        }
        else {
            LOGGER.error("El producto no existe en la BDD");
            throw  new ResourceNotFoundException("El producto no existe en la BDD");
        }
                return productoDisponibleSalidaDto;
            }


    @Override
    public ProductoSalidaDto buscarProductoPorNombre(ProductoEntradaDto productoEntradaDto) throws ResourceNotFoundException {
        String nombreProducto = productoEntradaDto.getNombre();
        Producto productoPorNombre = productoRepository.findByNombre(nombreProducto);

        ProductoSalidaDto productoEncontrado = null;
        if (productoPorNombre!= null){
            productoEncontrado = entidadAdtoSalida(productoPorNombre);
            LOGGER.info("Producto encontrado por nombre : " + productoPorNombre);
        } else{
            LOGGER.info("No se encontró el producto con el nombre : " + nombreProducto);
            throw new ResourceNotFoundException("No se encontró el producto con el nombre : " + nombreProducto);
        }

        return productoEncontrado;
    }

    @Override
    public List<ProductoSalidaDto> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
                    LOGGER.info("listados de productos: " + productos);
        List<ProductoSalidaDto> productoSalidaDtoList= new ArrayList<>();

        for (Producto p: productos){

            ProductoSalidaDto productoSalidaDto = entidadAdtoSalida(p);
            productoSalidaDtoList.add(productoSalidaDto);
        }
        LOGGER.info("Listado de todos los productos : " + productos);

        return productoSalidaDtoList;
    }



    private void configuracionMapper(){

        modelMapper.typeMap(Categoria.class, ProductoSalidaDto.class)
                .addMappings(mapper ->
                {
                    mapper.map(Categoria::getTitulo,ProductoSalidaDto::setTituloCategoria);
                });

        modelMapper.typeMap(ProductoEntradaDto.class, Producto.class)
        .addMappings(mapper -> mapper.map(ProductoEntradaDto::getImgenEntradaDtoProductos,Producto::setImagenes));

        modelMapper.typeMap(Producto.class, ProductoSalidaDto.class)
                .addMappings(mapper ->
                {
                    mapper.map(Producto::getImagenes,ProductoSalidaDto::setImagenSalidaDto);
                    mapper.map(Producto::getCaracteristicas,ProductoSalidaDto::setCaracteristicaSalidaDtos);
                    mapper.map(Producto::getFechasReservadas,ProductoSalidaDto::setFechasReservadasSalidaDTo);
                });


    }
    public Producto dtoEntradaAentidad(ProductoEntradaDto productoEntradaDto){
        return modelMapper.map(productoEntradaDto, Producto.class);
    }

    public ProductoSalidaDto entidadAdtoSalida(Producto producto){
        return modelMapper.map(producto, ProductoSalidaDto.class);
    }


    public Producto dtoModificacioAentidad (ProductoModificacionEntradaDto productoModificacionEntradaDto){
        return modelMapper.map(productoModificacionEntradaDto,Producto.class);
    }

}
