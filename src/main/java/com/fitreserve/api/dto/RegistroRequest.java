package com.fitreserve.api.dto;

import com.fitreserve.api.model.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegistroRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @Email(message = "El email no es válido")
        @NotBlank(message = "El email es obligatorio")
        String email,

        @Size(min = 6, message = "La contraseña debe tener mínimo 6 caracteres")
        String password,

        @NotNull(message = "El rol es obligatorio")
        Rol rol
) {
}
