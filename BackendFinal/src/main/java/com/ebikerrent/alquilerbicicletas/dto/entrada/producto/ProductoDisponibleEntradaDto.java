package com.ebikerrent.alquilerbicicletas.dto.entrada.producto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDisponibleEntradaDto {
    //@NotNull(message = "El producto no puede ser nulo")
    //@Pattern(regexp = "^\\s*[A-Z0-9][A-Z0-9\\s]*$", message = "El campo debe contener solo letras mayúsculas y números.")
    //@Pattern(regexp = "^\\s*[A-Z]*\\s*$|^\\s+$", message = "El nombre del producto debe estar en mayúsculas")
    @Pattern(regexp = "^[A-Z0-9\\s]*$", message = "El nombre del producto debe estar en mayúsculas y puede contener números")
    @Size(max = 250)
    private String nombreProducto;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de reserva")
    private LocalDate fechaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha de reserva")
    private LocalDate fechaFin;
}
