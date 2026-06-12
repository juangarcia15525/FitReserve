package com.fitreserve.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clases_mente_cuerpo")
@Getter
@Setter
@NoArgsConstructor
public class ClaseMenteCuerpo extends ClaseGimnasio {

    @Column(nullable = false)
    private Integer nivelRelajacion;

    @Column(nullable = false)
    private Boolean requiereEsterilla;
}
