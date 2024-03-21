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
@ToString
@Table(name = "CATEGORIAS")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "TITULO")
    private String titulo;

    @OneToMany(mappedBy = "categoria",fetch = FetchType.LAZY,cascade =CascadeType.ALL)
    @JsonIgnore
    private Set<Producto> productos=new HashSet<>();

    @Override
    public String toString() {
        return " \n Categoria{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +

                '}' + '\n';
    }
}

