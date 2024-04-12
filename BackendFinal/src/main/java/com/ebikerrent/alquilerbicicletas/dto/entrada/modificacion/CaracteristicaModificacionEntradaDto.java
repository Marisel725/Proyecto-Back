package com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaracteristicaModificacionEntradaDto {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String nombre;
    @NotNull
    @NotBlank
    private String icono;
}
