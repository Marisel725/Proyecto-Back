package com.ebikerrent.alquilerbicicletas.dto.salida.producto;

import com.ebikerrent.alquilerbicicletas.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.categoria.CategoriaSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.imagen.ImagenSalidaDtoProducto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductoSalidaDto {
    private Long id;
    private String nombre;
    private String descripcion;

    @JsonProperty("categoria")
    private CategoriaSalidaDto categoriaSalidaDto;

    @JsonProperty("imagenes")
    private List<ImagenSalidaDtoProducto> imagenSalidaDtoProductos = new ArrayList<>();

    @JsonProperty("caracteristicas")
    private List<CaracteristicaSalidaDto> caracteristicaSalidaDto = new ArrayList<>();
    @JsonProperty("reservas")
    private List<LocalDate> fechasReservadas = new ArrayList<>();





}
