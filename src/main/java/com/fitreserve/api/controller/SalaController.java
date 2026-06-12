package com.fitreserve.api.controller;

import com.fitreserve.api.dto.SalaRequest;
import com.fitreserve.api.model.Sala;
import com.fitreserve.api.service.SalaService;
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
@RequestMapping("/api/salas")
public class SalaController {

    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> findAll() {
        return salaService.findAll();
    }

    @GetMapping("/{id}")
    public Sala findById(@PathVariable Long id) {
        return salaService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Sala> create(@Valid @RequestBody SalaRequest request) {
        return ResponseEntity.status(201).body(salaService.create(request));
    }

    @PutMapping("/{id}")
    public Sala update(@PathVariable Long id, @Valid @RequestBody SalaRequest request) {
        return salaService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
