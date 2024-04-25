package com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ReservaModificacionEntradaDto {
    @NotNull
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de reserva")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @NotNull(message = "Debe especificarse la fecha de reserva")
    private LocalDate fechaFin;
    @NotNull(message = "El producto no puede ser nulo")
    private String producto;
}
