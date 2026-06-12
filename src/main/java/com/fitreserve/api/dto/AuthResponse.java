package com.fitreserve.api.dto;

import com.fitreserve.api.model.Rol;

public record AuthResponse(
        String token,
        String email,
        Rol rol
) {
}
