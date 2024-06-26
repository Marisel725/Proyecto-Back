package com.ebikerrent.alquilerbicicletas.dto.salida.usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
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
    private String telefono;
    private boolean esAdmin;

    @Override
    public String toString() {
        return "Usuario Registrado:" + '\n'+
                "ID=" + id + '\n'+
                "nombre='" + nombre + '\n' +
                "apellido='" + apellido + '\n' +
                "mail='" + mail + '\n' +
                "telefono='" + telefono + '\n' +
                "esAdmin=" + esAdmin + '\n';
    }
}
