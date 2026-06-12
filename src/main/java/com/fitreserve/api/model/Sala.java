package com.fitreserve.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "salas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer capacidadMaxima;

    @Column(nullable = false)
    private String ubicacion;

    @JsonIgnore
    @OneToMany(mappedBy = "sala")
    private List<ClaseGimnasio> clases = new ArrayList<>();
}
