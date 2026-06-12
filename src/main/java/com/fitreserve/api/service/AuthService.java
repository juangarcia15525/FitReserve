package com.fitreserve.api.service;

import com.fitreserve.api.dto.AuthResponse;
import com.fitreserve.api.dto.LoginRequest;
import com.fitreserve.api.dto.RegistroRequest;
import com.fitreserve.api.exception.BadRequestException;
import com.fitreserve.api.model.Usuario;
import com.fitreserve.api.repository.UsuarioRepository;
import com.fitreserve.api.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BadRequestException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNombre(request.nombre());
        usuario.setEmail(request.email());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(request.rol());

        Usuario savedUsuario = usuarioRepository.save(usuario);
        String token = jwtService.generateToken(savedUsuario);

        return new AuthResponse(token, savedUsuario.getEmail(), savedUsuario.getRol());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Email o contraseña incorrectos"));

        String token = jwtService.generateToken(usuario);
        return new AuthResponse(token, usuario.getEmail(), usuario.getRol());
    }
}
