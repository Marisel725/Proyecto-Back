package com.ebikerrent.alquilerbicicletas.dto.salida.puntuacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PuntuacionSalidaDto {
    private Long id;
    private int valor;
    private Long reservaId;
}
