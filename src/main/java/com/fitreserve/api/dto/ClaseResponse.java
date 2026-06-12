package com.fitreserve.api.dto;

import com.fitreserve.api.model.NivelIntensidad;

import java.time.LocalDateTime;

public record ClaseResponse(
        Long id,
        String tipo,
        String nombre,
        String descripcion,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer capacidad,
        NivelIntensidad nivelIntensidad,
        Long salaId,
        String salaNombre,
        Long entrenadorId,
        String entrenadorNombre,
        Integer caloriasEstimadas,
        String tipoCardio,
        String grupoMuscular,
        Boolean necesitaEquipamiento,
        Integer nivelRelajacion,
        Boolean requiereEsterilla
) {
}