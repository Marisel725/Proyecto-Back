package com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class ProductoModificacionEntradaDto {
    @NotNull
    private Long id;

    @NotNull(message = "Debe ingresar un nombre")
    @NotBlank(message = "Debe especificar un nombre")
    private String nombre;
    @NotNull(message = "Debe ingresar una descripcion")
    @NotBlank(message = "Debe especificar una descripcion")
    private String descripcion;

   // @Valid//private ImagenEntradaDto imagenEntradaDto;

   // @Valid
    //private CategoriaEntradaDto categoriaEntradaDto;
   @JsonProperty("categoria")
    private String categoria;
}

//Los DTOs de modificación (ProductoModificacionEntradaDto) se utilizan específicamente cuando se desea modificar un objeto existente.
//Pueden contener solo los campos que se permiten modificar y son diferentes de los DTOs de entrada y salida.
