package com.api.rest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voto {

    @Id
    @GeneratedValue
    @Column(name = "voto_id")
    private  Long id;

    @ManyToOne
    @JoinColumn(name = "opcion_id")
    private Opcion opcion;

}
