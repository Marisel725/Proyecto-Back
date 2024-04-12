package com.ebikerrent.alquilerbicicletas.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "USUARIOS")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="NOMBRE")
    @Size(min = 1, max = 250)
    private String nombre;

    @Column(name="APELLIDO")
    @Size(min = 1, max = 250)
    private String apellido;

    @Column(name="EMAIL",unique = true)
    @Size(min = 1, max = 250)
    private String mail;

    @Column(name="TELEFONO")
    private String telefono;

    @Column(name="PASSWORD")
    @Size(min = 1, max = 250)
    private String password;

    private boolean esAdmin;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Favorito> favorito = new ArrayList<>();
}
