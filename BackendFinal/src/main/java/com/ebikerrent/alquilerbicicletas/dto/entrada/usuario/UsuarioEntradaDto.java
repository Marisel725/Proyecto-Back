package com.ebikerrent.alquilerbicicletas.dto.entrada.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
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
    @NotNull(message = "El nombre del usuario no puede ser nulo")
    @NotBlank(message = "El nombre del usuario debe esepecificarse")
    @Size(min = 1, max = 250)
    private String nombre;

    @NotNull(message = "El apellido del usuario no puede ser nulo")
    @NotBlank(message = "El apellido del usuario debe esepecificarse")
    @Size(min = 1, max = 250)
    private String apellido;

    @NotNull(message = "El correo del usuario no puede ser nulo")
    @NotBlank(message = "El correo del usuario debe esepecificarse")
    @Size(min = 1, max = 250)
    @Email
    private String mail;

    @NotNull(message = "El telefono del usuario no puede ser nulo")
    @NotBlank(message = "El telefono del usuario debe especificarse")
    @Pattern(regexp = "\\d+", message = "Este campo solo admite numeros")
    @Size(min = 5, max = 20)
    private String telefono;

    @NotNull(message = "La contraseña del usuario no puede ser nula")
    @NotBlank(message = "La contraseña del usuario debe esepecificarse")
    @Size(min = 1, max = 250)
    private String password;
    private boolean esAdmin = false;

}
