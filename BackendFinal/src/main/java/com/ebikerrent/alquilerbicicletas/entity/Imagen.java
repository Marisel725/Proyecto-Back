package com.ebikerrent.alquilerbicicletas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
@Table(name = "IMAGENES")
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "URLIMG")
    private String urlImg;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    private Producto producto;

}

