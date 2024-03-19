package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ReservaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.reserva.ReservaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.reserva.ReservaSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.entity.Reserva;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.ProductoRepository;
import com.ebikerrent.alquilerbicicletas.repository.ReservaRepository;
import com.ebikerrent.alquilerbicicletas.service.IReservaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {
    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private final ProductoRepository productoRepository;
    private final ReservaRepository reservaRepository;
    private final ModelMapper modelMapper;

    public ReservaService(ProductoRepository productoRepository, ReservaRepository reservaRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.reservaRepository = reservaRepository;
        this.modelMapper = modelMapper;
        configuracionMapper();
    }

    @Override
    public List<ReservaSalidaDto> listarReservas() {
        List<Reserva> reservas = reservaRepository.findAll();

        List<ReservaSalidaDto> reservasSalidaDtoList = new ArrayList<>();

        for (Reserva r : reservas) {
            ReservaSalidaDto reservaSalidaDto = entidadAdtoSalida(r);
            reservasSalidaDtoList.add(reservaSalidaDto);
        }
        LOGGER.info("Listado de todas las reservas : " + reservas);
        return reservasSalidaDtoList;
    }

    @Override
    public ReservaSalidaDto registrarReserva(ReservaEntradaDto reservaEntradaDto) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(reservaEntradaDto.getProducto_id()).orElse(null);

        ReservaSalidaDto reservaGuardadaDto = null;

        LocalDate fechaInicio= reservaEntradaDto.getFechaInicio();
        LocalDate fechaFin= reservaEntradaDto.getFechaFin();
        List<LocalDate> fechaBuscada= new ArrayList<>();
        List<LocalDate>fechasReservadas= productoBuscado.getFechasReservadas();
        if (productoBuscado != null){
            if (fechaFin.compareTo(fechaInicio) >= 2){
                while (!fechaInicio.isAfter(fechaFin)){
                    fechaBuscada.add(fechaInicio);
                    fechaInicio = fechaInicio.plusDays(1);
                }
                for (LocalDate fecha: fechaBuscada){
                    LOGGER.info("Fecha" + fecha);
                    if (fechasReservadas.contains(fecha)){
                        LOGGER.error("la fecha" + fecha + "se encuentra reseravda");
                        throw new ResourceNotFoundException("La fecha" + fecha + "ya se encuentra reservada");
                    }else {
                        fechasReservadas.add(fecha);
                    }
                }

                Reserva reservaRecibida = dtoEntradaAentidad(reservaEntradaDto);
                reservaRecibida.setProducto(productoBuscado);

                Reserva reservaGuardada = reservaRepository.save(reservaRecibida);
                reservaGuardadaDto = entidadAdtoSalida(reservaGuardada);
                LOGGER.info("Reserva realizada con exito: " + reservaRecibida);
            }
            else {
                LOGGER.error("La fecha de reserva debe ser mayor a 48hs");
                throw  new ResourceNotFoundException("La fecha de reserva debe ser mayor a 48hs");
            }
        }
        else {
            LOGGER.error("El producto no existe en la BDD");
            throw  new ResourceNotFoundException("El producto no existe en la BDD");
        }
    return reservaGuardadaDto;
    }

    @Override
    public ReservaSalidaDto buscarReservaPorId(Long id) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException {

    }

    @Override
    public ReservaSalidaDto modificarReserva(ReservaModificacionEntradaDto reservaModificacionEntradaDto) throws ResourceNotFoundException {
        return null;
    }

    private void configuracionMapper(){
        modelMapper.typeMap(Producto.class, ReservaSalidaDto.class)
                .addMappings(mapper ->
                        mapper.map(Producto::getNombre, ReservaSalidaDto::setNombreProducto));
    }

    public Reserva dtoEntradaAentidad(ReservaEntradaDto reservaEntradaDto){
        return modelMapper.map(reservaEntradaDto, Reserva.class);
    }

    public ReservaSalidaDto entidadAdtoSalida(Reserva reserva){
        return modelMapper.map(reserva, ReservaSalidaDto.class);
    }

    public Reserva dtoModificadoAentidad( ReservaModificacionEntradaDto reservaModificacionEntradaDto){
        return modelMapper.map(reservaModificacionEntradaDto, Reserva.class);
    }
}
