package com.ebikerrent.alquilerbicicletas.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PRODUCTOS", uniqueConstraints = @UniqueConstraint(columnNames = {"nombre"}))
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Column(name = "PRECIO")
    private BigDecimal precio;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_id")
    private List<Imagen> imagenes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    private Categoria categoria;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(
            name = "productos_caracteristicas",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "caracteristica_id")
    )
    private Set <Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Reserva> reservas = new HashSet<>();

    private List<LocalDate> fechasReservadas = new ArrayList<>();

    @OneToMany(mappedBy = "producto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Favorito> favorito = new ArrayList<>();

    private int puntuacion;

    @Override
    public String toString() {
        return "Producto:" +'\n'+
                "ID" + id + '\n'+
                "nombre='" + nombre + '\n' +
                "descripcion='" + descripcion + '\n' +
                "precio=" + precio + '\n' +
                "Imagen Título:" + imagenes + '\n'+
                "Categoria:" + categoria + '\n'+
                "Caracteristicas:" + caracteristicas;
    }
}
