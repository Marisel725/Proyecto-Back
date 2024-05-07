package com.ebikerrent.alquilerbicicletas.dto.salida.reserva;

import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.puntuacion.PuntuacionSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.usuario.UsuarioSalidaDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaSalidaDto {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    @JsonProperty("producto")
    @JsonIgnoreProperties(value = {"id","descripcion","imagenes","categoria","caracteristicas","fechasReservadas","puntuacion"})
    private ProductoSalidaDto producto;
    private UsuarioSalidaDto usuario;
    @JsonProperty("puntuacion")
    private PuntuacionSalidaDto puntuacionSalidaDto;
}
