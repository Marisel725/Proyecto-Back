package com.ebikerrent.alquilerbicicletas.dto.salida.reserva;

import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.usuario.UsuarioSalidaDto;
import com.ebikerrent.alquilerbicicletas.entity.Producto;
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
    @JsonIgnoreProperties(value = {"id","descripcion","imagenes","categoria","caracteristicas","fechasReservadas"})
    private ProductoSalidaDto producto;
    private UsuarioSalidaDto usuario;
}
