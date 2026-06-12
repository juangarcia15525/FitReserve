package com.fitreserve.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clases_fuerza")
@Getter
@Setter
@NoArgsConstructor
public class ClaseFuerza extends ClaseGimnasio {

    @Column(nullable = false)
    private String grupoMuscular;

    @Column(nullable = false)
    private Boolean necesitaEquipamiento;
}
