package com.ebikerrent.alquilerbicicletas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Puntuacion {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "VALOR")
    @NotNull
    private Long valor;
    @OneToOne
    @JoinColumn(name = "reserva_id")
    private Reserva reserva;
}
