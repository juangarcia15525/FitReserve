package com.fitreserve.api.repository;

import com.fitreserve.api.model.ClaseGimnasio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ClaseGimnasioRepository extends JpaRepository<ClaseGimnasio, Long> {
    List<ClaseGimnasio> findByFechaInicioAfter(LocalDateTime fecha);
    List<ClaseGimnasio> findByEntrenadorId(Long entrenadorId);
}
