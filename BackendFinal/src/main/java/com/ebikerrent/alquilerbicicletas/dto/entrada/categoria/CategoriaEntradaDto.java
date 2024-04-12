package com.ebikerrent.alquilerbicicletas.dto.entrada.categoria;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoriaEntradaDto {
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
