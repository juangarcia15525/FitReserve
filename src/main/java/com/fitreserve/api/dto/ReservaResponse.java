package com.fitreserve.api.dto;

import com.fitreserve.api.model.EstadoReserva;

import java.time.LocalDateTime;

public record ReservaResponse(
        Long id,
        LocalDateTime fechaReserva,
        EstadoReserva estado,
        String nombreClase,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin
) {
}
