# FitReserve API - Diagrama actualizado

Este documento refleja el estado actual del proyecto FitReserve API: pantalla web en `/`, autenticacion JWT, controllers, services, repositories, entidades JPA, DTOs, MySQL y datos iniciales.

## Diagrama UML de dominio

```mermaid
classDiagram
    direction LR

    class Usuario {
        +Long id
        +String nombre
        +String email
        +String password
        +Rol rol
        +getAuthorities()
        +getUsername()
    }

    class Reserva {
        +Long id
        +LocalDateTime fechaReserva
        +EstadoReserva estado
    }

    class Sala {
        +Long id
        +String nombre
        +Integer capacidadMaxima
        +String ubicacion
    }

    class ClaseGimnasio {
        <<abstract>>
        +Long id
        +String nombre
        +String descripcion
        +LocalDateTime fechaInicio
        +LocalDateTime fechaFin
        +Integer capacidad
        +NivelIntensidad nivelIntensidad
    }

    class ClaseCardio {
        +Integer caloriasEstimadas
        +String tipoCardio
    }

    class ClaseFuerza {
        +String grupoMuscular
        +Boolean necesitaEquipamiento
    }

    class ClaseMenteCuerpo {
        +Integer nivelRelajacion
        +Boolean requiereEsterilla
    }

    class Rol {
        <<enumeration>>
        ADMIN
        TRAINER
        MEMBER
    }

    class EstadoReserva {
        <<enumeration>>
        ACTIVA
        CANCELADA
        COMPLETADA
    }

    class NivelIntensidad {
        <<enumeration>>
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

## Diagrama de arquitectura actual

```mermaid
flowchart TD
    UI["Pantalla web\n/\nindex.html"] --> AUTHC["AuthController\n/auth/register\n/auth/login"]
    UI --> SALAC["SalaController\n/api/salas"]
    UI --> CLASEC["ClaseGimnasioController\n/api/clases"]
    UI --> RESC["ReservaController\n/api/reservas"]

    AUTHC --> AUTHS["AuthService"]
    SALAC --> SALAS["SalaService"]
    CLASEC --> CLASES["ClaseGimnasioService"]
    RESC --> RESS["ReservaService"]

    AUTHS --> JWT["JwtService"]
    SECURITY["SecurityConfig\nJwtAuthenticationFilter\nCustomUserDetailsService"] --> JWT
    SECURITY --> AUTHC
    SECURITY --> SALAC
    SECURITY --> CLASEC
    SECURITY --> RESC

    AUTHS --> USR["UsuarioRepository"]
    SALAS --> SALAR["SalaRepository"]
    CLASES --> CLASEGR["ClaseGimnasioRepository"]
    CLASES --> CARDIOR["ClaseCardioRepository"]
    CLASES --> FUERZAR["ClaseFuerzaRepository"]
    CLASES --> MENTER["ClaseMenteCuerpoRepository"]
    RESS --> RESR["ReservaRepository"]
    RESS --> USR
    RESS --> CLASEGR

    INIT["DataInitializer\nusuarios, salas y clases iniciales"] --> USR
    INIT --> SALAR
    INIT --> CLASEGR

    USR --> DB[("MySQL\nfitreserve_db")]
    SALAR --> DB
    CLASEGR --> DB
    CARDIOR --> DB
    FUERZAR --> DB
    MENTER --> DB
    RESR --> DB
```

## DTOs principales

```mermaid
classDiagram
    class RegistroRequest {
        +String nombre
        +String email
        +String password
        +Rol rol
    }
    class LoginRequest {
        +String email
        +String password
    }
    class AuthResponse {
        +String token
        +String email
        +Rol rol
    }
    class SalaRequest {
        +String nombre
        +Integer capacidadMaxima
        +String ubicacion
    }
    class ClaseResponse {
        +Long id
        +String tipo
        +String nombre
        +String salaNombre
        +String entrenadorNombre
        +NivelIntensidad nivelIntensidad
    }
    class ReservaResponse {
        +Long id
        +LocalDateTime fechaReserva
        +EstadoReserva estado
        +String nombreClase
    }
```

## Flujo de uso visual

```mermaid
sequenceDiagram
    actor Usuario
    participant Web as Pantalla web /
    participant Auth as AuthController
    participant Api as Controllers API
    participant DB as MySQL fitreserve_db

    Usuario->>Web: Abre http://localhost:8080/
    Web->>Api: GET /api/salas y GET /api/clases
    Api->>DB: Consulta salas y clases
    DB-->>Api: Datos
    Api-->>Web: JSON
    Web-->>Usuario: Muestra tarjetas

    Usuario->>Web: Registra usuario o inicia sesion
    Web->>Auth: POST /auth/register o /auth/login
    Auth->>DB: Guarda/consulta Usuario
    Auth-->>Web: JWT Bearer token

    Usuario->>Web: Reserva una clase
    Web->>Api: POST /api/reservas/clases/{id} con Bearer token
    Api->>DB: Crea Reserva
    Api-->>Web: ReservaResponse
```

## Estado actual verificado

- `GET /` sirve la pantalla visual.
- `GET /api/salas` devuelve salas cargadas.
- `GET /api/clases` devuelve clases cargadas mediante `ClaseResponse`.
- `POST /auth/register` registra usuarios.
- `POST /auth/login` devuelve token JWT.
- `POST /api/reservas/clases/{id}` permite reservar con usuario autenticado.
- `GET /api/reservas/mis-reservas` devuelve reservas mediante `ReservaResponse`.