# FitReserve API

FitReserve API es una API REST creada con Java y Spring Boot para gestionar reservas de clases colectivas en un gimnasio. El proyecto incluye autenticacion Bearer con JWT, roles de usuario, CRUD de salas, CRUD de clases, reservas, validaciones, manejo global de errores y una pantalla web sencilla para probar la aplicacion desde el navegador.

Repositorio GitHub: https://github.com/juangarcia15525/FitReserve

## Descripcion del proyecto

La aplicacion permite gestionar un gimnasio con tres tipos principales de usuarios:

- ADMIN: puede crear, editar y borrar salas y clases.
- TRAINER: representa a los entrenadores que imparten clases.
- MEMBER: puede consultar clases y realizar reservas.

El sistema trabaja con salas, clases de gimnasio y reservas. Las clases usan herencia JPA con una clase padre abstracta `ClaseGimnasio` y tres clases hijas: `ClaseCardio`, `ClaseFuerza` y `ClaseMenteCuerpo`.

Tambien incluye una pantalla visual en:

```text
http://localhost:8080/
```

Desde esa pantalla se puede registrar usuarios, iniciar sesion, ver salas, ver clases, crear salas como ADMIN y hacer reservas.

## Funcionalidades

- Registro de usuarios.
- Inicio de sesion con JWT.
- Autenticacion Bearer Token.
- Autorizacion por roles: ADMIN, TRAINER y MEMBER.
- CRUD completo de salas.
- CRUD de clases de gimnasio.
- Herencia JPA con estrategia `JOINED`.
- Reservas de clases.
- Cancelacion de reservas.
- Validaciones de entrada con Bean Validation.
- Manejo global de errores con respuestas JSON.
- Datos iniciales de prueba mediante `DataInitializer`.
- Pantalla web local para probar el backend sin Postman.

## Diagramas

El repositorio incluye diagramas actualizados en la carpeta `docs`:

- `docs/diagrama-uml-nuevo.md`: diagrama UML actual para entregar.
- `docs/diagrama-actualizado.md`: diagrama completo de dominio y arquitectura.
- `docs/diagrama-casos-uso.md`: diagrama de casos de uso.

La herencia JPA principal es:

```java
@Inheritance(strategy = InheritanceType.JOINED)
```

Resumen del modelo:

- `Usuario` realiza muchas `Reserva`.
- `Usuario` tambien puede actuar como entrenador de muchas `ClaseGimnasio`.
- `Sala` contiene muchas `ClaseGimnasio`.
- `ClaseGimnasio` puede tener muchas `Reserva`.
- `ClaseCardio`, `ClaseFuerza` y `ClaseMenteCuerpo` heredan de `ClaseGimnasio`.

## Configuracion

### Requisitos

- Java 17.
- Maven.
- MySQL.
- IntelliJ IDEA o cualquier IDE compatible con Spring Boot.

### Base de datos

Crea la base de datos en MySQL:

```sql
CREATE DATABASE fitreserve_db;
```

### Contrasena de MySQL

Antes de ejecutar el proyecto, abre:

```text
src/main/resources/application.properties
```

Busca esta linea:

```properties
spring.datasource.password=TU_PASSWORD
```

Sustituye `TU_PASSWORD` por la contrasena de tu usuario MySQL.

Ejemplo:

```properties
spring.datasource.password=mi_password_mysql
```

Cada persona que descargue el proyecto debe poner su propia contrasena de MySQL. No necesita la contrasena del autor.

## Ejecucion

### Desde IntelliJ IDEA

1. Abre IntelliJ IDEA.
2. Ve a `File > Open`.
3. Selecciona la carpeta `fitreserve-api`.
4. Espera a que Maven cargue las dependencias.
5. Cambia `TU_PASSWORD` en `application.properties` por tu contrasena de MySQL.
6. Ejecuta `FitReserveApiApplication.java` con el boton verde.
7. Abre `http://localhost:8080/`.

### Desde terminal

```bash
mvn spring-boot:run
```

Mientras la aplicacion este arrancada, funciona en:

```text
http://localhost:8080/
```

Si se cierra IntelliJ o la terminal donde se esta ejecutando Spring Boot, la aplicacion se apaga.

## Tecnologias utilizadas

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- Maven
- Lombok
- Bean Validation
- HTML, CSS y JavaScript basico para la pantalla visual

## Estructura del proyecto

```text
src/main/java/com/fitreserve/api
|-- config
|-- controller
|-- dto
|-- exception
|-- model
|-- repository
|-- security
|-- service
```

## Controladores y rutas

### AuthController

```http
POST /auth/register
POST /auth/login
GET  /auth/register
GET  /auth/login
```

Los GET redirigen a la pantalla visual para evitar errores al abrir esas rutas desde el navegador.

### SalaController

```http
GET    /api/salas
GET    /api/salas/{id}
POST   /api/salas
PUT    /api/salas/{id}
DELETE /api/salas/{id}
```

Las operaciones de modificacion requieren rol ADMIN.

### ClaseGimnasioController

```http
GET    /api/clases
GET    /api/clases/disponibles
GET    /api/clases/{id}
POST   /api/clases/cardio
POST   /api/clases/fuerza
POST   /api/clases/mente-cuerpo
PUT    /api/clases/{id}
DELETE /api/clases/{id}
```

Consultar clases es publico. Crear, editar y borrar clases requiere rol ADMIN.

### ReservaController

```http
GET    /api/reservas
GET    /api/reservas/mis-reservas
POST   /api/reservas/clases/{claseId}
PATCH  /api/reservas/{reservaId}/cancelar
DELETE /api/reservas/{id}
```

Las reservas requieren usuario autenticado con rol MEMBER o ADMIN.

## Pruebas con Postman

El repositorio incluye ejemplos en:

```text
docs/postman-examples.http
```

Tambien se puede probar desde la pantalla visual en:

```text
http://localhost:8080/
```

Usuarios iniciales creados por `DataInitializer`:

```text
admin@test.com   / 123456 / ADMIN
trainer@test.com / 123456 / TRAINER
juan@test.com    / 123456 / MEMBER
```

## Reglas de negocio

- No se puede reservar una clase que ya ha empezado.
- No se puede reservar dos veces la misma clase con una reserva activa.
- No se puede reservar una clase llena.
- No se puede cancelar una reserva si la clase ya empezo.
- No se puede crear una clase con mas capacidad que la sala.
- Solo ADMIN puede crear, editar y borrar salas y clases.

## Gestion de tareas

La planificacion del proyecto esta documentada en:

```text
docs/tareas-proyecto.md
```

En una entrega real tambien se puede copiar esta planificacion a Trello, GitHub Projects o Notion y anadir aqui la URL publica.

## Enlaces adicionales

- Repositorio GitHub: https://github.com/juangarcia15525/FitReserve
- Aplicacion local: http://localhost:8080/
- Diagrama UML: `docs/diagrama-uml-nuevo.md`
- Casos de uso: `docs/diagrama-casos-uso.md`
- Ejemplos HTTP/Postman: `docs/postman-examples.http`
- Presentacion final: `docs/presentacion-fitreserve.md`
- Script para crear Google Slides: `docs/crear-presentacion-google-slides.gs`

## Trabajo futuro

- Anadir Swagger/OpenAPI.
- Anadir tests unitarios y de integracion.
- Anadir filtros por fecha, intensidad y sala.
- Anadir paginacion.
- Anadir gestion avanzada de entrenadores.
- Desplegar la aplicacion en un servidor cloud.

## Recursos

- Documentacion oficial de Spring Boot.
- Documentacion oficial de Spring Security.
- Documentacion oficial de Spring Data JPA.
- Documentacion oficial de MySQL.
- Documentacion de JWT.

## Licencia

Proyecto academico creado para entrega final. Uso educativo.

## Miembros del equipo

- Juan Garcia


