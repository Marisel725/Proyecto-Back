package com.ebikerrent.alquilerbicicletas.dto.entrada.producto;

import com.ebikerrent.alquilerbicicletas.dto.entrada.imagen.ImagenEntradaDtoProducto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoEntradaDto {
    @NotNull (message = "El nombre del producto no puede ser nula.")
    @NotBlank(message = "El nombre debe especificarse.")
    @Pattern(regexp = "^[A-Z0-9]+(\\s+[A-Z0-9]+)*$", message = "El campo debe contener solo letras mayúsculas y espacios entre palabras.")
    //@Pattern(regexp = "^[A-Z0-9]*$", message = "El campo debe contener solo letras mayúsculas.")
    @Size(min = 1, max = 250)
    private String nombre;

    @NotNull(message = "La descripción del producto no puede ser nula.")
    @NotBlank(message = "La descripción debe especificarse.")
    @Size(min = 1, max = 250)
    private String descripcion;

    @Valid //Valida en su propia clase
    @JsonProperty("imagenes")
    private Set<ImagenEntradaDtoProducto> imagenEntradaDtoProductos = new HashSet<>();

    @NotNull(message = "El nombre de la categoría no puede ser nulo.")
    @JsonProperty("categoria")
    private String categoriaString;

    //@NotNull (message = "La caracteristica del producto debe especificarse")
    @JsonProperty("caracteristicas")
    private Set <String> caracteristica_nombre;

}