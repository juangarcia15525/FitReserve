package com.fitreserve.api.controller;

import com.fitreserve.api.dto.AuthResponse;
import com.fitreserve.api.dto.LoginRequest;
import com.fitreserve.api.dto.RegistroRequest;
import com.fitreserve.api.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping({"/register", "/login"})
    public void redirectAuthPages(HttpServletResponse response) throws IOException {
        response.sendRedirect("/");
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegistroRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}