package com.ebikerrent.alquilerbicicletas.dto.salida.reserva;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

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
    private String nombreProducto;
}
