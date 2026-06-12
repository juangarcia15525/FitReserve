package com.fitreserve.api.controller;

import com.fitreserve.api.dto.ReservaResponse;
import com.fitreserve.api.model.Usuario;
import com.fitreserve.api.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<ReservaResponse> findAll() {
        return reservaService.findAll();
    }

    @GetMapping("/mis-reservas")
    public List<ReservaResponse> misReservas(@AuthenticationPrincipal Usuario usuario) {
        return reservaService.findByUsuario(usuario);
    }

    @PostMapping("/clases/{claseId}")
    public ResponseEntity<ReservaResponse> crearReserva(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long claseId
    ) {
        return ResponseEntity.status(201).body(reservaService.crearReserva(usuario, claseId));
    }

    @PatchMapping("/{reservaId}/cancelar")
    public ReservaResponse cancelar(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long reservaId
    ) {
        return reservaService.cancelarReserva(usuario, reservaId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}