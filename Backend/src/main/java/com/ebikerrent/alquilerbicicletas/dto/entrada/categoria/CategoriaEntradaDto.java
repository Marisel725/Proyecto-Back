package com.ebikerrent.alquilerbicicletas.dto.entrada.categoria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaEntradaDto {
    @NotNull(message = "Este campo Categoria no debe ser nulo")
    @NotBlank(message = "Este campo no debe estar vacío")
    private String titulo;

    //@NotNull(message = "Debe ingresar una imagen")
    //@NotBlank(message = "Este campo no puede estar vacio")
    //private String imagen;

}
