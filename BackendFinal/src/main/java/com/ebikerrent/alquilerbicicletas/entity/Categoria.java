package com.ebikerrent.alquilerbicicletas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CATEGORIAS")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TTULO", unique = true)
    private String titulo;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @Column(name = "IMAGEN")
    private String imagen;

    @OneToMany(mappedBy = "categoria",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    @Override
    public String toString() {
        return " \n Categoria{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +

                '}' + '\n';
    }

}

