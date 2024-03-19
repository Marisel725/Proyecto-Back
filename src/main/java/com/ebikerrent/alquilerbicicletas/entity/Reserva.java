package com.ebikerrent.alquilerbicicletas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "RESERVAS")
public class Reserva {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fechaInicio")
    @NotNull
    private LocalDate fechaInicio;
    @Column(name = "fechaFinal")
    @NotNull
    private LocalDate fechaFin;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                '}';
    }
}
