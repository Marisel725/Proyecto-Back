package com.ebikerrent.alquilerbicicletas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="NOMBRE")
    @NotNull
    @Size(min = 1, max = 250)
    private String nombre;
    @Column(name="APELLIDO")
    @NotNull
    @Size(min = 1, max = 250)
    private String apellido;
    @Column(name="EMAIL",unique = true)
    @NotNull
    @Size(min = 1, max = 250)
    private String mail;
    @Column(name="password")
    @NotNull
    @Size(min = 1, max = 250)
    private String password;

    private boolean esAdmin;

}
