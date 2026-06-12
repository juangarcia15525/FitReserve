# Diagrama UML actualizado - FitReserve API

Este diagrama representa el proyecto actual de FitReserve API: backend Spring Boot con JPA, MySQL, JWT, usuarios, salas, clases y reservas.

```mermaid
classDiagram
    direction LR

    class Usuario {
        <<Entity>>
        +Long id
        +String nombre
        +String email
        +String password
        +Rol rol
        +getAuthorities()
        +getUsername()
        +isEnabled()
    }

    class Reserva {
        <<Entity>>
        +Long id
        +LocalDateTime fechaReserva
        +EstadoReserva estado
    }

    class Sala {
        <<Entity>>
        +Long id
        +String nombre
        +Integer capacidadMaxima
        +String ubicacion
    }

    class ClaseGimnasio {
        <<abstract Entity>>
        +Long id
        +String nombre
        +String descripcion
        +LocalDateTime fechaInicio
        +LocalDateTime fechaFin
        +Integer capacidad
        +NivelIntensidad nivelIntensidad
    }

    class ClaseCardio {
        <<Entity>>
        +Integer caloriasEstimadas
        +String tipoCardio
    }

    class ClaseFuerza {
        <<Entity>>
        +String grupoMuscular
        +Boolean necesitaEquipamiento
    }

    class ClaseMenteCuerpo {
        <<Entity>>
        +Integer nivelRelajacion
        +Boolean requiereEsterilla
    }

    class Rol {
        <<enum>>
        ADMIN
        TRAINER
        MEMBER
    }

    class EstadoReserva {
        <<enum>>
        ACTIVA
        CANCELADA
        COMPLETADA
    }

    class NivelIntensidad {
        <<enum>>
        BAJO
        MEDIO
        ALTO
    }

    Usuario "1" --> "0..*" Reserva : realiza
    Usuario "1" --> "0..*" ClaseGimnasio : entrenador
    Sala "1" --> "0..*" ClaseGimnasio : contiene
    ClaseGimnasio "1" --> "0..*" Reserva : recibe

    ClaseGimnasio <|-- ClaseCardio
    ClaseGimnasio <|-- ClaseFuerza
    ClaseGimnasio <|-- ClaseMenteCuerpo

    Usuario --> Rol
    Reserva --> EstadoReserva
    ClaseGimnasio --> NivelIntensidad
```

## Notas del modelo actual

- `ClaseGimnasio` es abstracta y usa herencia JPA con `JOINED`.
- `Usuario` implementa `UserDetails` para integrarse con Spring Security.
- Un usuario puede hacer muchas reservas.
- Un usuario con rol de entrenador puede impartir muchas clases.
- Una sala contiene muchas clases.
- Una clase puede tener muchas reservas.
- Las subclases actuales son `ClaseCardio`, `ClaseFuerza` y `ClaseMenteCuerpo`.

## Diagrama de arquitectura actual

```mermaid
flowchart LR
    Web["Pantalla web\nlocalhost:8080"] --> Controllers["Controllers\nAuth, Sala, Clase, Reserva"]
    Controllers --> Services["Services\nAuthService, SalaService,\nClaseGimnasioService, ReservaService"]
    Services --> Repositories["Repositories\nSpring Data JPA"]
    Repositories --> MySQL[("MySQL\nfitreserve_db")]

    Security["Spring Security + JWT\nBearer Token"] --> Controllers
    DTOs["DTOs + records\nvalidaciones"] --> Controllers
    Exceptions["GlobalExceptionHandler\nerrores JSON"] --> Controllers
```
