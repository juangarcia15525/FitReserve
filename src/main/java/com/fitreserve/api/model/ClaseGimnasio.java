package com.fitreserve.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clases_gimnasio")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public abstract class ClaseGimnasio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 1000)
    private String descripcion;

    @Column(nullable = false)
    private LocalDateTime fechaInicio;

    @Column(nullable = false)
    private LocalDateTime fechaFin;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelIntensidad nivelIntensidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id", nullable = false)
    @JsonIgnoreProperties({"clases"})
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrenador_id", nullable = false)
    @JsonIgnoreProperties({"password", "reservas", "clasesComoEntrenador", "authorities", "username", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled"})
    private Usuario entrenador;

    @JsonIgnore
    @OneToMany(mappedBy = "claseGimnasio")
    private List<Reserva> reservas = new ArrayList<>();
}
