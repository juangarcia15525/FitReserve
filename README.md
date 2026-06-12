# FitReserve API

FitReserve API es una API REST desarrollada con Java y Spring Boot para gestionar reservas de clases colectivas en un gimnasio.

## Funcionalidades

- Pantalla visual en http://localhost:8080/ para registrar usuarios, iniciar sesion, crear salas, ver clases y reservar.

- Registro e inicio de sesiÃ³n de usuarios.
- AutenticaciÃ³n Bearer con JWT.
- Roles: ADMIN, TRAINER y MEMBER.
- CRUD completo de salas.
- CRUD de clases de gimnasio.
- Herencia JPA con `ClaseGimnasio` como clase abstracta.
- Tipos de clase: `ClaseCardio`, `ClaseFuerza` y `ClaseMenteCuerpo`.
- Reservar plaza en una clase.
- Cancelar reservas.
- Validaciones de entrada.
- Manejo global de errores.

## TecnologÃ­as

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Maven
- Lombok
- Validation

## Diagrama UML

El diagrama actualizado del proyecto esta en:

- `docs/diagrama-actualizado.md`

Incluye:

- Diagrama UML de dominio con `Usuario`, `Reserva`, `Sala`, `ClaseGimnasio` y sus subclases.
- Enumeraciones `Rol`, `EstadoReserva` y `NivelIntensidad`.
- Diagrama de arquitectura con pantalla web, controllers, services, repositories, seguridad JWT y MySQL.
- Flujo visual de registro, login, consulta de salas/clases y reservas.

La herencia JPA se implementa con:

```java
@Inheritance(strategy = InheritanceType.JOINED)
```
## ConfiguraciÃ³n de MySQL

Crea la base de datos:

```sql
CREATE DATABASE fitreserve_db;
```

Edita `src/main/resources/application.properties` y cambia la contraseÃ±a:

```properties
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
```

## Ejecutar en IntelliJ IDEA

1. Abre IntelliJ IDEA.
2. Ve a `File > Open`.
3. Selecciona la carpeta `fitreserve-api`.
4. Espera a que Maven cargue las dependencias.
5. Cambia la contraseÃ±a de MySQL en `application.properties`.
6. Ejecuta `FitReserveApiApplication.java` con el botÃ³n verde.

TambiÃ©n puedes ejecutar desde terminal:

```bash
mvn spring-boot:run
```

## Rutas principales

### Auth

```http
POST /auth/register
POST /auth/login
```

### Salas

Requieren rol ADMIN.

```http
GET /api/salas
GET /api/salas/{id}
POST /api/salas
PUT /api/salas/{id}
DELETE /api/salas/{id}
```

### Clases

Consultar clases es pÃºblico. Crear, editar y borrar requiere ADMIN.

```http
GET /api/clases
GET /api/clases/disponibles
GET /api/clases/{id}
POST /api/clases/cardio
POST /api/clases/fuerza
POST /api/clases/mente-cuerpo
PUT /api/clases/{id}
DELETE /api/clases/{id}
```

### Reservas

Requieren usuario autenticado con rol MEMBER o ADMIN.

```http
GET /api/reservas
GET /api/reservas/mis-reservas
POST /api/reservas/clases/{claseId}
PATCH /api/reservas/{reservaId}/cancelar
DELETE /api/reservas/{id}
```

## Ejemplos para Postman

### Registrar admin

```http
POST http://localhost:8080/auth/register
Content-Type: application/json
```

```json
{
  "nombre": "Admin",
  "email": "admin@test.com",
  "password": "123456",
  "rol": "ADMIN"
}
```

### Registrar entrenador

```json
{
  "nombre": "Entrenador",
  "email": "trainer@test.com",
  "password": "123456",
  "rol": "TRAINER"
}
```

### Registrar socio

```json
{
  "nombre": "Juan",
  "email": "juan@test.com",
  "password": "123456",
  "rol": "MEMBER"
}
```

### Login

```http
POST http://localhost:8080/auth/login
Content-Type: application/json
```

```json
{
  "email": "admin@test.com",
  "password": "123456"
}
```

Copia el token y Ãºsalo en Postman:

```text
Authorization > Bearer Token
```

### Crear sala como ADMIN

```http
POST http://localhost:8080/api/salas
Authorization: Bearer TU_TOKEN_ADMIN
Content-Type: application/json
```

```json
{
  "nombre": "Sala Spinning",
  "capacidadMaxima": 25,
  "ubicacion": "Primera planta"
}
```

### Crear clase de cardio como ADMIN

```http
POST http://localhost:8080/api/clases/cardio
Authorization: Bearer TU_TOKEN_ADMIN
Content-Type: application/json
```

```json
{
  "nombre": "Spinning",
  "descripcion": "Clase intensa de bicicleta",
  "fechaInicio": "2026-08-20T10:00:00",
  "fechaFin": "2026-08-20T11:00:00",
  "capacidad": 20,
  "nivelIntensidad": "ALTO",
  "salaId": 1,
  "entrenadorId": 2,
  "caloriasEstimadas": 500,
  "tipoCardio": "Bicicleta"
}
```

### Reservar clase como MEMBER

```http
POST http://localhost:8080/api/reservas/clases/1
Authorization: Bearer TU_TOKEN_MEMBER
```

### Cancelar reserva

```http
PATCH http://localhost:8080/api/reservas/1/cancelar
Authorization: Bearer TU_TOKEN_MEMBER
```

## Reglas de negocio implementadas

- No se puede reservar una clase que ya ha empezado.
- No se puede reservar dos veces la misma clase con una reserva activa.
- No se puede reservar una clase llena.
- No se puede cancelar una reserva si la clase ya empezÃ³.
- No se puede crear una clase con mÃ¡s capacidad que la sala.
- Solo ADMIN puede crear, editar y borrar salas y clases.

## Trabajo futuro

- AÃ±adir Swagger/OpenAPI.
- AÃ±adir tests unitarios.
- AÃ±adir filtros por fecha, intensidad y sala.
- AÃ±adir paginaciÃ³n.
- AÃ±adir gestiÃ³n avanzada de entrenadores.

## Miembros del equipo

- Juan
