package com.fitreserve.api.service;

import com.fitreserve.api.dto.SalaRequest;
import com.fitreserve.api.exception.ResourceNotFoundException;
import com.fitreserve.api.model.Sala;
import com.fitreserve.api.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private final SalaRepository salaRepository;

    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    public Sala findById(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con id: " + id));
    }

    public Sala create(SalaRequest request) {
        Sala sala = new Sala();
        sala.setNombre(request.nombre());
        sala.setCapacidadMaxima(request.capacidadMaxima());
        sala.setUbicacion(request.ubicacion());
        return salaRepository.save(sala);
    }

    public Sala update(Long id, SalaRequest request) {
        Sala sala = findById(id);
        sala.setNombre(request.nombre());
        sala.setCapacidadMaxima(request.capacidadMaxima());
        sala.setUbicacion(request.ubicacion());
        return salaRepository.save(sala);
    }

    public void delete(Long id) {
        Sala sala = findById(id);
        salaRepository.delete(sala);
    }
}
