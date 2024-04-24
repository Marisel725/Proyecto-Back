package com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    @NotNull(message = "Debe ingresar un titulo de Categoria")
    @NotBlank(message = "Debe especificar un titulo de Categoria")
    @JsonProperty("categoria")
    private String tituloCategoria;
    //@NotNull(message = "Debe ingresar una caracteristica")
    //@NotBlank(message = "Debe especificar una caracteristica")
    @NotEmpty
    @JsonProperty("caracteristicas")
    private Set<String> caracteristicas = new HashSet<>();

}


