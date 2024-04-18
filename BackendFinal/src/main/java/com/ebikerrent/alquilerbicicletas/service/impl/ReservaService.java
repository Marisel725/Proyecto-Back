package com.ebikerrent.alquilerbicicletas.service.impl;

import com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion.ReservaModificacionEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.entrada.reserva.ReservaEntradaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.reserva.ReservaSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
import com.ebikerrent.alquilerbicicletas.entity.Reserva;
import com.ebikerrent.alquilerbicicletas.entity.Usuario;
import com.ebikerrent.alquilerbicicletas.exceptions.ResourceNotFoundException;
import com.ebikerrent.alquilerbicicletas.repository.ProductoRepository;
import com.ebikerrent.alquilerbicicletas.repository.PuntuacionRepository;
import com.ebikerrent.alquilerbicicletas.repository.ReservaRepository;
import com.ebikerrent.alquilerbicicletas.repository.UsuarioRepository;
import com.ebikerrent.alquilerbicicletas.service.IReservaService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaService implements IReservaService {
    private final Logger LOGGER = LoggerFactory.getLogger(ReservaService.class);
    private final ProductoRepository productoRepository;
    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final PuntuacionRepository puntuacionRepository;
    private final ModelMapper modelMapper;

    public ReservaService(ProductoRepository productoRepository, ReservaRepository reservaRepository, UsuarioRepository usuarioRepository, PuntuacionRepository puntuacionRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.puntuacionRepository = puntuacionRepository;
        this.modelMapper = modelMapper;
        configuracionMapper();
    }

    @Override
    public ReservaSalidaDto registrarReserva(ReservaEntradaDto reservaEntradaDto) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(reservaEntradaDto.getProducto_id()).orElse(null);
        Usuario usuarioBuscado = usuarioRepository.findByMail(reservaEntradaDto.getCorreo());

        ReservaSalidaDto reservaGuardadaDto;
        LocalDate fechaInicio = reservaEntradaDto.getFechaInicio();
        LocalDate fechaFin = reservaEntradaDto.getFechaFin();

        if (productoBuscado == null) {
            LOGGER.info("No existe un producto con ID: " + reservaEntradaDto.getProducto_id());
            throw new ResourceNotFoundException("No existe un producto con ID: " + reservaEntradaDto.getProducto_id());
        }

        if (usuarioBuscado == null) {
            LOGGER.info("No existe un usuario con correo: " + reservaEntradaDto.getCorreo());
            throw new ResourceNotFoundException("No existe un usuario con correo: " + reservaEntradaDto.getCorreo());
        }

        List<LocalDate> fechasReservadas = productoBuscado.getFechasReservadas();

        boolean verificacion = buscarReservaPorProducto(reservaEntradaDto);

        if (verificacion) {
            for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
                productoBuscado.getFechasReservadas().add(fecha);
            }
            Reserva reservaRecibida = dtoEntradaAentidad(reservaEntradaDto);
            reservaRecibida.setProducto(productoBuscado);
            reservaRecibida.setUsuario(usuarioBuscado);
            Reserva reservaGuardada = reservaRepository.save(reservaRecibida);
            reservaGuardadaDto = entidadAdtoSalida(reservaGuardada);
        } else {
            LOGGER.error("El producto no existe en la BDD");
            throw new ResourceNotFoundException("El producto no existe en la BDD");
        }

        return reservaGuardadaDto;
    }
    @Override
    public List<ReservaSalidaDto> listarReservas() {
        List<Reserva> reservas = reservaRepository.findAll();

        List<ReservaSalidaDto> reservasSalidaDtoList = new ArrayList<>();

        for (Reserva r : reservas) {
            ReservaSalidaDto reservaSalidaDto = entidadAdtoSalida(r);
            reservasSalidaDtoList.add(reservaSalidaDto);
        }
        LOGGER.info("Listado de todas las reservas : " + '\n'+ reservas);
        return reservasSalidaDtoList;
    }
    public boolean buscarReservaPorProducto(ReservaEntradaDto reservaEntradaDto) throws ResourceNotFoundException {
        Producto productoBuscado = productoRepository.findById(reservaEntradaDto.getProducto_id()).orElse(null);

        LocalDate fechaInicio = reservaEntradaDto.getFechaInicio();
        LocalDate fechaFin = reservaEntradaDto.getFechaFin();

        List<LocalDate> fechasReservadas = productoBuscado.getFechasReservadas();


        if (productoBuscado == null) {
            LOGGER.error("El producto no existe en la BDD");
            throw new ResourceNotFoundException("El producto no existe en la BDD");
        }

        if (ChronoUnit.DAYS.between(fechaInicio, fechaFin) < 2) {
            LOGGER.error("La fecha de reserva debe ser mayor a 48hs");
            throw new ResourceNotFoundException("La fecha de reserva debe ser mayor a 48hs");
        }

        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)) {
            if (fechasReservadas.contains(fecha)) {

                LOGGER.info("La fecha: " + fecha + " ya se encuentra reservada");
                throw new ResourceNotFoundException("La fecha: " + fecha + " ya se encuentra reservada");

            }
        }

        LOGGER.info("El producto se encuentra disponible para las fechas buscadas: de " + fechaInicio + " a " + fechaFin + " " + productoBuscado.getNombre());
        return true;
    }

    @Override
    public List<ReservaSalidaDto> listarReservasDeUsuario(String mail) throws ResourceNotFoundException {
        Usuario usuarioBuscado = usuarioRepository.findByMail(mail);

        if (usuarioBuscado == null){
            LOGGER.error("No existe un usuario con ese correo:"+ mail);
            throw new ResourceNotFoundException("No existe un usuario con ese correo:"+ mail);
        }
        List<Reserva> reservasDelUsuario = reservaRepository.findByUsuario(usuarioBuscado);
        List<ReservaSalidaDto> reservaSalidaDtoList = new ArrayList<>();

        for (Reserva reserva : reservasDelUsuario){
            ReservaSalidaDto reservaSalidaDto = entidadAdtoSalida(reserva);
            reservaSalidaDtoList.add(reservaSalidaDto);
        }
        LOGGER.info("Listado de todas las reservas del usuario con correo " + mail + '\n'  + reservasDelUsuario);
        return reservaSalidaDtoList;
    }


    @Override
    public ReservaSalidaDto buscarReservaPorId(Long id) throws ResourceNotFoundException {
        Reserva reservaBuscada = reservaRepository.findById(id).orElse(null);
        ReservaSalidaDto reservaSalidaDto;

        if (reservaBuscada != null) {
            reservaSalidaDto = entidadAdtoSalida(reservaBuscada);
            LOGGER.info("Reserva encontrada:" + reservaSalidaDto);

        } else {
            LOGGER.info("No se encontro la reserva con ID: " + id);
            throw new ResourceNotFoundException("No se encontro la reserva con ID: " + id);
        }
        return reservaSalidaDto;
    }


    @Override
    public void eliminarReserva(Long id) throws ResourceNotFoundException {
        Reserva reservaBuscada = reservaRepository.findById(id).orElse(null);
        LocalDate fechaInicio = reservaBuscada.getFechaInicio();
        LocalDate fechaFin = reservaBuscada.getFechaFin();

        if(reservaBuscada == null){
            LOGGER.info("No se encontro la reserva con ID: " + id);
            throw new ResourceNotFoundException("No se encontro la reserva con ID: " + id);
        }
        Producto productoAsociado = reservaBuscada.getProducto();
        List<LocalDate> fechasReservadas = productoAsociado.getFechasReservadas();
        for (LocalDate fecha = fechaInicio; !fecha.isAfter(fechaFin); fecha = fecha.plusDays(1)){
            final  LocalDate fechaActual = fecha;
            fechasReservadas.removeIf(fechaReservada -> fechaReservada.equals((fechaActual)));
        }
        productoAsociado.setFechasReservadas(fechasReservadas);
        productoRepository.save(productoAsociado);
        reservaRepository.delete(reservaBuscada);
        LOGGER.info("Se eliminó la reserva ID: " + id);

    }

    @Override
    public ReservaSalidaDto modificarReserva(ReservaModificacionEntradaDto reservaModificacionEntradaDto) throws ResourceNotFoundException {
        return null;
    }

    private void configuracionMapper() {
        modelMapper.typeMap(Reserva.class, ReservaSalidaDto.class)
                .addMappings(mapper ->
                        {
                            mapper.map(Reserva::getProducto, ReservaSalidaDto::setProducto);
                            mapper.map(Reserva::getUsuario, ReservaSalidaDto::setUsuario);
                            mapper.map(Reserva::getPuntuacion, ReservaSalidaDto::setPuntuacionSalidaDto);
                        });
    }

    public Reserva dtoEntradaAentidad(ReservaEntradaDto reservaEntradaDto) {
        return modelMapper.map(reservaEntradaDto, Reserva.class);
    }

    public ReservaSalidaDto entidadAdtoSalida(Reserva reserva) {
        return modelMapper.map(reserva, ReservaSalidaDto.class);
    }

    public Reserva dtoModificadoAentidad(ReservaModificacionEntradaDto reservaModificacionEntradaDto) {
        return modelMapper.map(reservaModificacionEntradaDto, Reserva.class);
    }
}
