package com.ebikerrent.alquilerbicicletas.dto.salida.categoria;

import com.ebikerrent.alquilerbicicletas.dto.salida.producto.ProductoSalidaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoriaSalidaDto {
    private int id;
    private String titulo;

}