package com.fitreserve.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SalaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotNull(message = "La capacidad máxima es obligatoria")
        @Min(value = 1, message = "La capacidad debe ser mayor que 0")
        Integer capacidadMaxima,

        @NotBlank(message = "La ubicación es obligatoria")
        String ubicacion
) {
}
