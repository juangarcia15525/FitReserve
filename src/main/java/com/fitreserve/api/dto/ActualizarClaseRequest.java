package com.fitreserve.api.dto;

import com.fitreserve.api.model.NivelIntensidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ActualizarClaseRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "La descripción es obligatoria")
        String descripcion,

        @NotNull(message = "La fecha de inicio es obligatoria")
        @Future(message = "La fecha de inicio debe ser futura")
        LocalDateTime fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria")
        @Future(message = "La fecha de fin debe ser futura")
        LocalDateTime fechaFin,

        @NotNull(message = "La capacidad es obligatoria")
        @Min(value = 1, message = "La capacidad debe ser mayor que 0")
        Integer capacidad,

        @NotNull(message = "El nivel de intensidad es obligatorio")
        NivelIntensidad nivelIntensidad,

        @NotNull(message = "La sala es obligatoria")
        Long salaId,

        @NotNull(message = "El entrenador es obligatorio")
        Long entrenadorId
) {
}
