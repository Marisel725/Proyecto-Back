package com.ebikerrent.alquilerbicicletas.dto.entrada.producto;

import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImgenEntradaDtoProducto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoEntradaDto {
    @NotNull (message = "El nombre del producto no puede ser nula")
    @NotBlank(message = "El nombre debe especificarse")
    @Size(min = 1, max = 250)
    private String nombre;

    @NotNull(message = "La descripción del producto no puede ser nula")
    @NotBlank(message = "La descripción debe especificarse")
    @Size(min = 1, max = 250)
    private String descripcion;

    @NotNull(message = "La categoria del producto no puede ser nula")
    @NotBlank(message = "La categoria debe especificarse")
    @JsonProperty("categoria")
    private String categoria;

    @JsonProperty("caracteristicas_nombre")
    @NotNull (message = "La caracteristica del producto debe especificarse")

    private Set<String> caracteristicas_nombre;
    @JsonProperty("imagenes")
    @Valid
    private Set<ImgenEntradaDtoProducto> imgenEntradaDtoProductos;

}