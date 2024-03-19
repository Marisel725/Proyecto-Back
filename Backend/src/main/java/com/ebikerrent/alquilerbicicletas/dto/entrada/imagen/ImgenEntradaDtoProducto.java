package com.ebikerrent.alquilerbicicletas.dto.entrada.imagen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImgenEntradaDtoProducto {
    @NotNull(message = "Este campo no debe ser nulo")
    @NotBlank(message = "Este campo no debe estar vacío")
    private String titulo;
    @NotNull(message = "Este campo no debe ser nulo")
    @NotBlank(message = "Este campo no debe estar vacío")
    private String urlImg;
}
