package com.fitreserve.api.repository;

import com.fitreserve.api.model.EstadoReserva;
import com.fitreserve.api.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndClaseGimnasioIdAndEstado(
            Long usuarioId,
            Long claseGimnasioId,
            EstadoReserva estado
    );

    long countByClaseGimnasioIdAndEstado(Long claseGimnasioId, EstadoReserva estado);
}
