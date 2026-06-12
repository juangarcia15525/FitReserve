# Gestion de tareas - FitReserve API

Este documento resume la planificacion usada para dividir el proyecto en tareas pequenas. Puede copiarse a Trello, GitHub Projects o Notion si se necesita una URL externa para la entrega.

## Backlog inicial

- Definir la idea del proyecto.
- Crear el diagrama UML inicial.
- Definir modelos principales: Usuario, Sala, ClaseGimnasio y Reserva.
- Elegir estrategia de herencia JPA.
- Preparar base de datos MySQL.
- Crear estructura de paquetes.

## Tareas completadas

### Configuracion base

- Crear proyecto Spring Boot.
- Configurar Maven y dependencias.
- Configurar conexion con MySQL.
- Crear base de datos `fitreserve_db`.
- Configurar `application.properties`.

### Modelo de datos

- Crear enum `Rol`.
- Crear enum `EstadoReserva`.
- Crear enum `NivelIntensidad`.
- Crear entidad `Usuario`.
- Crear entidad `Sala`.
- Crear entidad abstracta `ClaseGimnasio`.
- Crear entidad `ClaseCardio`.
- Crear entidad `ClaseFuerza`.
- Crear entidad `ClaseMenteCuerpo`.
- Crear entidad `Reserva`.
- Configurar relaciones JPA.
- Configurar herencia JPA `JOINED`.

### Repositorios

- Crear `UsuarioRepository`.
- Crear `SalaRepository`.
- Crear `ClaseGimnasioRepository`.
- Crear repositorios de subclases.
- Crear `ReservaRepository`.

### Seguridad

- Crear servicio JWT.
- Crear filtro JWT.
- Crear `CustomUserDetailsService`.
- Configurar Spring Security.
- Proteger rutas por roles.

### Servicios y controladores

- Crear `AuthService` y `AuthController`.
- Crear `SalaService` y `SalaController`.
- Crear `ClaseGimnasioService` y `ClaseGimnasioController`.
- Crear `ReservaService` y `ReservaController`.
- Crear DTOs de entrada y salida.
- Crear manejo global de excepciones.

### Interfaz visual y pruebas

- Crear pantalla web local en `src/main/resources/static/index.html`.
- Permitir registro, login, listado de salas y clases.
- Permitir crear salas como ADMIN.
- Permitir reservar clases como MEMBER.
- Crear datos iniciales con `DataInitializer`.
- Probar rutas principales desde navegador y HTTP.

### Documentacion

- Crear README completo.
- Crear diagrama UML actualizado.
- Crear diagrama de arquitectura.
- Crear diagrama de casos de uso.
- Documentar rutas principales.
- Documentar configuracion de MySQL.
- Subir el proyecto a GitHub.

## Pendiente / mejoras futuras

- Crear tablero publico de Trello o GitHub Projects.
- Crear presentacion online en Google Slides.
- Anadir Swagger/OpenAPI.
- Anadir tests unitarios y de integracion.
- Desplegar en un servidor cloud.
