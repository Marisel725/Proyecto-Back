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
public class CategoriaModificacionEntradaDto {
    @NotNull
    private Long id;
    @NotNull(message = "Debe ingresar un titulo")
    @NotBlank(message = "Debe especificar un titulo")
    private String titulo;
    @NotNull(message = "Debe ingresar una descripcion")
    @NotBlank(message = "Debe especificar una descripcion")
    private String descripcion;
    @NotNull(message = "Debe ingresar una imagen")
    @NotBlank(message = "Este campo no puede estar vacio")
    private String imagen;

}
