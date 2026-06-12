package com.fitreserve.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clases_cardio")
@Getter
@Setter
@NoArgsConstructor
public class ClaseCardio extends ClaseGimnasio {

    @Column(nullable = false)
    private Integer caloriasEstimadas;

    @Column(nullable = false)
    private String tipoCardio;
}
