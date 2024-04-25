package com.ebikerrent.alquilerbicicletas.dto.salida.imagen;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ImagenSalidaDtoProducto {
    private Long id;
    private String titulo;
    private String urlImg;
}
