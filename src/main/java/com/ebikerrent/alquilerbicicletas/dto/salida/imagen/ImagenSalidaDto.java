package com.ebikerrent.alquilerbicicletas.dto.salida.imagen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ImagenSalidaDto {
    private Long id;

    private String titulo;

    private String urlImg;

    //private ProductoSalidaDto productoSalidaDto;

    private String nombreProducto; //Me muestra el nombre del producto con cuál lo asocié a traves de su id
}
