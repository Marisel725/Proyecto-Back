package com.ebikerrent.alquilerbicicletas.dto.entrada.favorito;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
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
public class FavoritoEntrada {
    @NotNull(message = "El usuario no puede ser nulo")
    @NotBlank(message = "El usuario debe esepecificarse")
    @Email
    private String correo;

    @NotNull(message = "El producto no puede ser nulo")
    private Long producto_id;

    @NotNull(message =  "Se debe marcar el producto como favorito")
    @JsonProperty("favorito")
    private Boolean favorito;
}
