package com.ebikerrent.alquilerbicicletas.dto.entrada.modificacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImagenModificacionEntradaDto {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String titulo;
    @NotNull
    @NotBlank
    private String urlImg;

}
