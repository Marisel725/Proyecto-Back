package com.ebikerrent.alquilerbicicletas.dto.entrada.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class UsuarioEntradaDto {
    @NotNull
    @Size(min = 1, max = 250)
    private String nombre;
    @NotNull
    @Size(min = 1, max = 250)
    private String apellido;

    @NotNull
    @Size(min = 1, max = 250)
    private String mail;

    @NotNull
    @Size(min = 1, max = 250)
    private String password;
    private boolean esAdmin = false;

}
