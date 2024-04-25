package com.ebikerrent.alquilerbicicletas.dto.entrada.caracteristica;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class CaracteristicaEntradaDto {
    @NotNull(message = "El nombre de la caracteristica no puede ser nulo")
    @NotBlank(message = "La caracteristica debe especificarse")
    @Size(min = 1, max = 250)
    private String nombre;
    @NotNull (message = "El icono de la caracteristica no puede ser nulo")
    @NotBlank (message = "El icono debe especificarse")
    @Size (min = 1, max = 250)
    private String icono;

}
