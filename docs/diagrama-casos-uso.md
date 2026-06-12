# Diagrama de casos de uso - FitReserve API

Este diagrama resume como interactuan los actores principales con la aplicacion.

```mermaid
flowchart LR
    Visitante["Visitante"]
    Member["MEMBER\nSocio"]
    Trainer["TRAINER\nEntrenador"]
    Admin["ADMIN\nAdministrador"]

    UC1((Registrarse))
    UC2((Iniciar sesion))
    UC3((Ver salas))
    UC4((Ver clases))
    UC5((Reservar clase))
    UC6((Ver mis reservas))
    UC7((Cancelar reserva))
    UC8((Crear sala))
    UC9((Editar sala))
    UC10((Eliminar sala))
    UC11((Crear clase))
    UC12((Editar clase))
    UC13((Eliminar clase))
    UC14((Gestionar usuarios por rol))

    Visitante --> UC1
    Visitante --> UC2
    Visitante --> UC3
    Visitante --> UC4

    Member --> UC2
    Member --> UC3
    Member --> UC4
    Member --> UC5
    Member --> UC6
    Member --> UC7

    Trainer --> UC2
    Trainer --> UC3
    Trainer --> UC4

    Admin --> UC2
    Admin --> UC3
    Admin --> UC4
    Admin --> UC5
    Admin --> UC6
    Admin --> UC7
    Admin --> UC8
    Admin --> UC9
    Admin --> UC10
    Admin --> UC11
    Admin --> UC12
    Admin --> UC13
    Admin --> UC14
```

## Actores

- Visitante: puede registrarse, iniciar sesion y consultar informacion publica.
- MEMBER: puede reservar clases y gestionar sus reservas.
- TRAINER: representa al entrenador asignado a las clases.
- ADMIN: puede gestionar salas, clases y consultar la informacion completa.

## Casos de uso principales

- Registro e inicio de sesion.
- Consulta de salas y clases.
- Creacion, edicion y eliminacion de salas.
- Creacion, edicion y eliminacion de clases.
- Reserva y cancelacion de clases.
- Autorizacion por roles mediante JWT.
