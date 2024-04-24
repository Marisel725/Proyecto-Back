package com.ebikerrent.alquilerbicicletas.dto.entrada.puntuacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PuntuacionEntradaDto {
    @NotNull(message = "El id de la reserva no puede ser nula.")
    private Long reservaId;
    @NotNull (message = "El valor de la puntuacion no puede ser nula.")
    private Long valor;
}
