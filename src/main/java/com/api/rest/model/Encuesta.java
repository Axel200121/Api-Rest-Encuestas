package com.api.rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Encuesta {

    @Id
    @GeneratedValue
    @Column(name = "encuesta_id")
    private Long id;

    @Column(name = "pregunta")
    private String pregunta;

    @OneToMany(cascade = CascadeType.ALL) //relacion uno a muchos
    @JoinColumn(name = "encuesta_id")
    @OrderBy // se va ordenar por opciones
    private Set<Opcion> opciones;

}
