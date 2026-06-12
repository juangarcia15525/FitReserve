package com.fitreserve.api.config;

import com.fitreserve.api.model.ClaseCardio;
import com.fitreserve.api.model.ClaseFuerza;
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
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(
            UsuarioRepository usuarioRepository,
            SalaRepository salaRepository,
            ClaseGimnasioRepository claseGimnasioRepository,
            ClaseCardioRepository claseCardioRepository,
            ClaseFuerzaRepository claseFuerzaRepository,
            ClaseMenteCuerpoRepository claseMenteCuerpoRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Usuario admin = usuarioRepository.findByEmail("admin@test.com")
                    .orElseGet(() -> usuarioRepository.save(usuario("Admin", "admin@test.com", Rol.ADMIN, passwordEncoder)));
            Usuario trainer = usuarioRepository.findByEmail("trainer@test.com")
                    .orElseGet(() -> usuarioRepository.save(usuario("Entrenador", "trainer@test.com", Rol.TRAINER, passwordEncoder)));
            usuarioRepository.findByEmail("juan@test.com")
                    .orElseGet(() -> usuarioRepository.save(usuario("Juan", "juan@test.com", Rol.MEMBER, passwordEncoder)));

            if (salaRepository.count() == 0) {
                salaRepository.saveAll(List.of(
                        sala("Sala Spinning", 25, "Primera planta"),
                        sala("Sala Fuerza", 18, "Zona peso libre"),
                        sala("Sala Yoga", 20, "Segunda planta")
                ));
            }

            if (claseGimnasioRepository.count() == 0) {
                List<Sala> salas = salaRepository.findAll();
                Sala spinning = salas.get(0);
                Sala fuerza = salas.size() > 1 ? salas.get(1) : spinning;
                Sala yoga = salas.size() > 2 ? salas.get(2) : spinning;

                claseCardioRepository.save(cardio(spinning, trainer));
                claseFuerzaRepository.save(fuerza(fuerza, trainer));
                claseMenteCuerpoRepository.save(menteCuerpo(yoga, trainer));
            }
        };
    }

    private Usuario usuario(String nombre, String email, Rol rol, PasswordEncoder passwordEncoder) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(passwordEncoder.encode("123456"));
        usuario.setRol(rol);
        return usuario;
    }

    private Sala sala(String nombre, Integer capacidadMaxima, String ubicacion) {
        Sala sala = new Sala();
        sala.setNombre(nombre);
        sala.setCapacidadMaxima(capacidadMaxima);
        sala.setUbicacion(ubicacion);
        return sala;
    }

    private ClaseCardio cardio(Sala sala, Usuario entrenador) {
        ClaseCardio clase = new ClaseCardio();
        completarClase(clase, "Spinning", "Clase intensa de bicicleta", 1, 10, 20, NivelIntensidad.ALTO, sala, entrenador);
        clase.setCaloriasEstimadas(500);
        clase.setTipoCardio("Bicicleta");
        return clase;
    }

    private ClaseFuerza fuerza(Sala sala, Usuario entrenador) {
        ClaseFuerza clase = new ClaseFuerza();
        completarClase(clase, "Full Body", "Entrenamiento de fuerza para todo el cuerpo", 1, 12, 16, NivelIntensidad.MEDIO, sala, entrenador);
        clase.setGrupoMuscular("Cuerpo completo");
        clase.setNecesitaEquipamiento(true);
        return clase;
    }

    private ClaseMenteCuerpo menteCuerpo(Sala sala, Usuario entrenador) {
        ClaseMenteCuerpo clase = new ClaseMenteCuerpo();
        completarClase(clase, "Yoga Flow", "Sesion de movilidad, respiracion y equilibrio", 2, 9, 18, NivelIntensidad.BAJO, sala, entrenador);
        clase.setNivelRelajacion(8);
        clase.setRequiereEsterilla(true);
        return clase;
    }

    private void completarClase(
            com.fitreserve.api.model.ClaseGimnasio clase,
            String nombre,
            String descripcion,
            int diasDesdeHoy,
            int hora,
            int capacidad,
            NivelIntensidad nivel,
            Sala sala,
            Usuario entrenador
    ) {
        LocalDateTime inicio = LocalDateTime.of(LocalDate.now().plusDays(diasDesdeHoy), LocalTime.of(hora, 0));
        clase.setNombre(nombre);
        clase.setDescripcion(descripcion);
        clase.setFechaInicio(inicio);
        clase.setFechaFin(inicio.plusHours(1));
        clase.setCapacidad(capacidad);
        clase.setNivelIntensidad(nivel);
        clase.setSala(sala);
        clase.setEntrenador(entrenador);
    }
}