package com.ebikerrent.alquilerbicicletas.dto.entrada.producto;

import com.ebikerrent.alquilerbicicletas.entity.Categoria;
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
public class ProductoPorCategoria {
    @NotNull(message = "El nombre de la categoría no puede ser nulo.")
    @NotBlank(message = "La categoria debe especificarse")
    @JsonProperty("categoria")
    private String nombreCategoria;
}
