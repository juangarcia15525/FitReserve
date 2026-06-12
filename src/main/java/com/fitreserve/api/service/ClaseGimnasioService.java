package com.fitreserve.api.service;

import com.fitreserve.api.dto.ActualizarClaseRequest;
import com.fitreserve.api.dto.ClaseCardioRequest;
import com.fitreserve.api.dto.ClaseFuerzaRequest;
import com.fitreserve.api.dto.ClaseMenteCuerpoRequest;
import com.fitreserve.api.dto.ClaseResponse;
import com.fitreserve.api.exception.BadRequestException;
import com.fitreserve.api.exception.ResourceNotFoundException;
import com.fitreserve.api.model.ClaseCardio;
import com.fitreserve.api.model.ClaseFuerza;
import com.fitreserve.api.model.ClaseGimnasio;
import com.fitreserve.api.model.ClaseMenteCuerpo;
import com.fitreserve.api.model.NivelIntensidad;
import com.fitreserve.api.model.Rol;
import com.fitreserve.api.model.Sala;
import com.fitreserve.api.model.Usuario;
import com.fitreserve.api.repository.ClaseCardioRepository;
import com.fitreserve.api.repository.ClaseFuerzaRepository;
import com.fitreserve.api.repository.ClaseGimnasioRepository;
import com.fitreserve.api.repository.ClaseMenteCuerpoRepository;
import com.fitreserve.api.repository.SalaRepository;
import com.fitreserve.api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClaseGimnasioService {

    private final ClaseGimnasioRepository claseGimnasioRepository;
    private final ClaseCardioRepository claseCardioRepository;
    private final ClaseFuerzaRepository claseFuerzaRepository;
    private final ClaseMenteCuerpoRepository claseMenteCuerpoRepository;
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;

    public ClaseGimnasioService(
            ClaseGimnasioRepository claseGimnasioRepository,
            ClaseCardioRepository claseCardioRepository,
            ClaseFuerzaRepository claseFuerzaRepository,
            ClaseMenteCuerpoRepository claseMenteCuerpoRepository,
            SalaRepository salaRepository,
            UsuarioRepository usuarioRepository
    ) {
        this.claseGimnasioRepository = claseGimnasioRepository;
        this.claseCardioRepository = claseCardioRepository;
        this.claseFuerzaRepository = claseFuerzaRepository;
        this.claseMenteCuerpoRepository = claseMenteCuerpoRepository;
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<ClaseResponse> findAll() {
        return claseGimnasioRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ClaseResponse> findDisponibles() {
        return claseGimnasioRepository.findByFechaInicioAfter(LocalDateTime.now()).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClaseResponse findById(Long id) {
        return toResponse(findClaseById(id));
    }

    @Transactional
    public ClaseResponse createCardio(ClaseCardioRequest request) {
        Sala sala = getSala(request.salaId());
        Usuario entrenador = getEntrenador(request.entrenadorId());
        validarClase(request.fechaInicio(), request.fechaFin(), request.capacidad(), sala);

        ClaseCardio clase = new ClaseCardio();
        rellenarCamposComunes(clase, request.nombre(), request.descripcion(), request.fechaInicio(),
                request.fechaFin(), request.capacidad(), request.nivelIntensidad(), sala, entrenador);
        clase.setCaloriasEstimadas(request.caloriasEstimadas());
        clase.setTipoCardio(request.tipoCardio());

        return toResponse(claseCardioRepository.save(clase));
    }

    @Transactional
    public ClaseResponse createFuerza(ClaseFuerzaRequest request) {
        Sala sala = getSala(request.salaId());
        Usuario entrenador = getEntrenador(request.entrenadorId());
        validarClase(request.fechaInicio(), request.fechaFin(), request.capacidad(), sala);

        ClaseFuerza clase = new ClaseFuerza();
        rellenarCamposComunes(clase, request.nombre(), request.descripcion(), request.fechaInicio(),
                request.fechaFin(), request.capacidad(), request.nivelIntensidad(), sala, entrenador);
        clase.setGrupoMuscular(request.grupoMuscular());
        clase.setNecesitaEquipamiento(request.necesitaEquipamiento());

        return toResponse(claseFuerzaRepository.save(clase));
    }

    @Transactional
    public ClaseResponse createMenteCuerpo(ClaseMenteCuerpoRequest request) {
        Sala sala = getSala(request.salaId());
        Usuario entrenador = getEntrenador(request.entrenadorId());
        validarClase(request.fechaInicio(), request.fechaFin(), request.capacidad(), sala);

        ClaseMenteCuerpo clase = new ClaseMenteCuerpo();
        rellenarCamposComunes(clase, request.nombre(), request.descripcion(), request.fechaInicio(),
                request.fechaFin(), request.capacidad(), request.nivelIntensidad(), sala, entrenador);
        clase.setNivelRelajacion(request.nivelRelajacion());
        clase.setRequiereEsterilla(request.requiereEsterilla());

        return toResponse(claseMenteCuerpoRepository.save(clase));
    }

    @Transactional
    public ClaseResponse update(Long id, ActualizarClaseRequest request) {
        ClaseGimnasio clase = findClaseById(id);
        Sala sala = getSala(request.salaId());
        Usuario entrenador = getEntrenador(request.entrenadorId());
        validarClase(request.fechaInicio(), request.fechaFin(), request.capacidad(), sala);

        rellenarCamposComunes(clase, request.nombre(), request.descripcion(), request.fechaInicio(),
                request.fechaFin(), request.capacidad(), request.nivelIntensidad(), sala, entrenador);

        return toResponse(claseGimnasioRepository.save(clase));
    }

    @Transactional
    public void delete(Long id) {
        ClaseGimnasio clase = findClaseById(id);
        claseGimnasioRepository.delete(clase);
    }

    private ClaseGimnasio findClaseById(Long id) {
        return claseGimnasioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase no encontrada con id: " + id));
    }

    private Sala getSala(Long salaId) {
        return salaRepository.findById(salaId)
                .orElseThrow(() -> new ResourceNotFoundException("Sala no encontrada con id: " + salaId));
    }

    private Usuario getEntrenador(Long entrenadorId) {
        Usuario usuario = usuarioRepository.findById(entrenadorId)
                .orElseThrow(() -> new ResourceNotFoundException("Entrenador no encontrado con id: " + entrenadorId));

        if (usuario.getRol() != Rol.TRAINER && usuario.getRol() != Rol.ADMIN) {
            throw new BadRequestException("El usuario seleccionado no puede ser entrenador");
        }

        return usuario;
    }

    private void validarClase(LocalDateTime fechaInicio, LocalDateTime fechaFin, Integer capacidad, Sala sala) {
        if (!fechaFin.isAfter(fechaInicio)) {
            throw new BadRequestException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        if (capacidad > sala.getCapacidadMaxima()) {
            throw new BadRequestException("La capacidad de la clase no puede superar la capacidad de la sala");
        }
    }

    private void rellenarCamposComunes(
            ClaseGimnasio clase,
            String nombre,
            String descripcion,
            LocalDateTime fechaInicio,
            LocalDateTime fechaFin,
            Integer capacidad,
            NivelIntensidad nivelIntensidad,
            Sala sala,
            Usuario entrenador
    ) {
        clase.setNombre(nombre);
        clase.setDescripcion(descripcion);
        clase.setFechaInicio(fechaInicio);
        clase.setFechaFin(fechaFin);
        clase.setCapacidad(capacidad);
        clase.setNivelIntensidad(nivelIntensidad);
        clase.setSala(sala);
        clase.setEntrenador(entrenador);
    }

    private ClaseResponse toResponse(ClaseGimnasio clase) {
        String tipo = "GENERAL";
        Integer caloriasEstimadas = null;
        String tipoCardio = null;
        String grupoMuscular = null;
        Boolean necesitaEquipamiento = null;
        Integer nivelRelajacion = null;
        Boolean requiereEsterilla = null;

        if (clase instanceof ClaseCardio cardio) {
            tipo = "CARDIO";
            caloriasEstimadas = cardio.getCaloriasEstimadas();
            tipoCardio = cardio.getTipoCardio();
        } else if (clase instanceof ClaseFuerza fuerza) {
            tipo = "FUERZA";
            grupoMuscular = fuerza.getGrupoMuscular();
            necesitaEquipamiento = fuerza.getNecesitaEquipamiento();
        } else if (clase instanceof ClaseMenteCuerpo menteCuerpo) {
            tipo = "MENTE_CUERPO";
            nivelRelajacion = menteCuerpo.getNivelRelajacion();
            requiereEsterilla = menteCuerpo.getRequiereEsterilla();
        }

        return new ClaseResponse(
                clase.getId(),
                tipo,
                clase.getNombre(),
                clase.getDescripcion(),
                clase.getFechaInicio(),
                clase.getFechaFin(),
                clase.getCapacidad(),
                clase.getNivelIntensidad(),
                clase.getSala().getId(),
                clase.getSala().getNombre(),
                clase.getEntrenador().getId(),
                clase.getEntrenador().getNombre(),
                caloriasEstimadas,
                tipoCardio,
                grupoMuscular,
                necesitaEquipamiento,
                nivelRelajacion,
                requiereEsterilla
        );
    }
}