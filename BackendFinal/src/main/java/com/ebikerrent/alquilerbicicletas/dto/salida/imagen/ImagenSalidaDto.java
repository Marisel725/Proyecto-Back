package com.ebikerrent.alquilerbicicletas.dto.salida.imagen;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ImagenSalidaDto {
    private Long id;
    private String titulo;
    private String urlImg;

    private String nombreProducto;
    /*@JsonProperty("productos")
    private ProductoSalidaDto productoSalidaDto;*/

}
