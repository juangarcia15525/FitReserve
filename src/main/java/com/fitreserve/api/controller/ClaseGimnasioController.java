package com.fitreserve.api.controller;

import com.fitreserve.api.dto.ActualizarClaseRequest;
import com.fitreserve.api.dto.ClaseCardioRequest;
import com.fitreserve.api.dto.ClaseFuerzaRequest;
import com.fitreserve.api.dto.ClaseMenteCuerpoRequest;
import com.fitreserve.api.dto.ClaseResponse;
import com.fitreserve.api.service.ClaseGimnasioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clases")
public class ClaseGimnasioController {

    private final ClaseGimnasioService claseGimnasioService;

    public ClaseGimnasioController(ClaseGimnasioService claseGimnasioService) {
        this.claseGimnasioService = claseGimnasioService;
    }

    @GetMapping
    public List<ClaseResponse> findAll() {
        return claseGimnasioService.findAll();
    }

    @GetMapping("/disponibles")
    public List<ClaseResponse> findDisponibles() {
        return claseGimnasioService.findDisponibles();
    }

    @GetMapping("/{id}")
    public ClaseResponse findById(@PathVariable Long id) {
        return claseGimnasioService.findById(id);
    }

    @PostMapping("/cardio")
    public ResponseEntity<ClaseResponse> createCardio(@Valid @RequestBody ClaseCardioRequest request) {
        return ResponseEntity.status(201).body(claseGimnasioService.createCardio(request));
    }

    @PostMapping("/fuerza")
    public ResponseEntity<ClaseResponse> createFuerza(@Valid @RequestBody ClaseFuerzaRequest request) {
        return ResponseEntity.status(201).body(claseGimnasioService.createFuerza(request));
    }

    @PostMapping("/mente-cuerpo")
    public ResponseEntity<ClaseResponse> createMenteCuerpo(@Valid @RequestBody ClaseMenteCuerpoRequest request) {
        return ResponseEntity.status(201).body(claseGimnasioService.createMenteCuerpo(request));
    }

    @PutMapping("/{id}")
    public ClaseResponse update(@PathVariable Long id, @Valid @RequestBody ActualizarClaseRequest request) {
        return claseGimnasioService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claseGimnasioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}