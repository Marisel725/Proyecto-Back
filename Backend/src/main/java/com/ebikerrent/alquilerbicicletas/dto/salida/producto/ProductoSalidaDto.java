package com.ebikerrent.alquilerbicicletas.dto.salida.producto;

import com.ebikerrent.alquilerbicicletas.dto.salida.caracteristica.CaracteristicaSalidaDto;
import com.ebikerrent.alquilerbicicletas.dto.salida.imagen.ImagenSalidaDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductoSalidaDto {
    private Long id;
    private String nombre;
    private String descripcion;

    @JsonProperty("imagenes")
    private Set<ImagenSalidaDto> imagenSalidaDto= new HashSet<>();

    @JsonProperty("caracteristica")
    private List<CaracteristicaSalidaDto> caracteristicaSalidaDtos = new ArrayList<>();
    @JsonProperty("categoria")
    private String tituloCategoria;

    private List<LocalDate> fechasReservadasSalidaDTo = new ArrayList<>();

}
