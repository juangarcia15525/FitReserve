package com.fitreserve.api.service;

import com.fitreserve.api.dto.ReservaResponse;
import com.fitreserve.api.exception.BadRequestException;
import com.fitreserve.api.exception.ResourceNotFoundException;
import com.fitreserve.api.model.ClaseGimnasio;
import com.fitreserve.api.model.EstadoReserva;
import com.fitreserve.api.model.Reserva;
import com.fitreserve.api.model.Rol;
import com.fitreserve.api.model.Usuario;
import com.fitreserve.api.repository.ClaseGimnasioRepository;
import com.fitreserve.api.repository.ReservaRepository;
import com.fitreserve.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClaseGimnasioRepository claseGimnasioRepository;

    public ReservaService(
            ReservaRepository reservaRepository,
            UsuarioRepository usuarioRepository,
            ClaseGimnasioRepository claseGimnasioRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.usuarioRepository = usuarioRepository;
        this.claseGimnasioRepository = claseGimnasioRepository;
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> findAll() {
        return reservaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> findByUsuario(Usuario usuario) {
        return reservaRepository.findByUsuarioId(usuario.getId())
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public ReservaResponse crearReserva(Usuario usuario, Long claseId) {
        ClaseGimnasio clase = claseGimnasioRepository.findById(claseId)
                .orElseThrow(() -> new ResourceNotFoundException("Clase no encontrada con id: " + claseId));

        if (clase.getFechaInicio().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("No se puede reservar una clase que ya ha empezado");
        }

        boolean yaReservada = reservaRepository.existsByUsuarioIdAndClaseGimnasioIdAndEstado(
                usuario.getId(),
                claseId,
                EstadoReserva.ACTIVA
        );

        if (yaReservada) {
            throw new BadRequestException("Ya tienes una reserva activa para esta clase");
        }

        long reservasActivas = reservaRepository.countByClaseGimnasioIdAndEstado(claseId, EstadoReserva.ACTIVA);

        if (reservasActivas >= clase.getCapacidad()) {
            throw new BadRequestException("La clase esta llena");
        }

        Reserva reserva = new Reserva();
        reserva.setUsuario(usuario);
        reserva.setClaseGimnasio(clase);
        reserva.setFechaReserva(LocalDateTime.now());
        reserva.setEstado(EstadoReserva.ACTIVA);

        return toResponse(reservaRepository.save(reserva));
    }

    @Transactional
    public ReservaResponse cancelarReserva(Usuario usuario, Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + reservaId));

        boolean esSuReserva = reserva.getUsuario().getId().equals(usuario.getId());
        boolean esAdmin = usuario.getRol() == Rol.ADMIN;

        if (!esSuReserva && !esAdmin) {
            throw new BadRequestException("No puedes cancelar una reserva de otro usuario");
        }

        if (reserva.getClaseGimnasio().getFechaInicio().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("No se puede cancelar una reserva de una clase que ya ha empezado");
        }

        reserva.setEstado(EstadoReserva.CANCELADA);
        return toResponse(reservaRepository.save(reserva));
    }

    @Transactional
    public void delete(Long id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        reservaRepository.delete(reserva);
    }

    private ReservaResponse toResponse(Reserva reserva) {
        ClaseGimnasio clase = reserva.getClaseGimnasio();
        return new ReservaResponse(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getEstado(),
                clase.getNombre(),
                clase.getFechaInicio(),
                clase.getFechaFin()
        );
    }
}