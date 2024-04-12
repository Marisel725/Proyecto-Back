package com.ebikerrent.alquilerbicicletas.dto.salida.caracteristica;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CaracteristicaSalidaDto {
    private Long id;
    private String nombre;
    private String icono;

    /*@JsonProperty("productos")
    private ProductoSalidaDto productoSalidaDtos;*/
}
