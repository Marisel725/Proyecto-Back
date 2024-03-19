package com.ebikerrent.alquilerbicicletas.dto.salida.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class UsuarioSalidaDto {
    private Long id;
    private String nombre;
    private String apellido;
    private String mail;
    private boolean esAdmin;
}