# Presentacion FitReserve API

Duracion objetivo: 5 minutos

- 3 minutos de explicacion
- 2 minutos de demostracion
- Total: 9 diapositivas

## Diapositiva 1 - Titulo

**FitReserve API**

Backend de reservas de clases de gimnasio

Juan Garcia

**Notas para hablar:**
Hola, soy Juan Garcia y voy a presentar FitReserve API, un backend creado con Java, Spring Boot y MySQL para gestionar reservas de clases en un gimnasio.

## Diapositiva 2 - Sobre mi

**Sobre mi**

- Me interesa el desarrollo backend.
- Queria practicar una API REST real con seguridad.
- Elegi un gimnasio porque tiene usuarios, clases, salas y reservas.

**Notas para hablar:**
Para este proyecto queria practicar una aplicacion parecida a un caso real. Un gimnasio me parecia una buena idea porque permite trabajar con relaciones entre tablas, roles de usuario y reglas de negocio.

## Diapositiva 3 - El problema

**Reservar clases necesita orden y control**

- Los socios necesitan ver clases disponibles.
- El gimnasio necesita controlar salas y capacidad.
- Los administradores necesitan gestionar clases y reservas.
- El sistema debe evitar reservas duplicadas o clases llenas.

**Notas para hablar:**
El problema principal es organizar las reservas. Si todo se hace manualmente, es facil duplicar reservas, superar la capacidad o perder informacion. FitReserve centraliza ese proceso.

## Diapositiva 4 - Que es FitReserve

**FitReserve conecta usuarios, salas, clases y reservas**

- Registro e inicio de sesion.
- Roles: ADMIN, TRAINER y MEMBER.
- CRUD de salas.
- CRUD de clases.
- Reservas y cancelaciones.
- Pantalla web local para probar la API.

**Notas para hablar:**
FitReserve permite registrar usuarios, iniciar sesion, consultar clases, reservar plaza y administrar salas y clases. Tiene una pantalla visual para probarlo sin depender solo de Postman.

## Diapositiva 5 - Modelo de datos

**El modelo usa relaciones JPA y herencia JOINED**

- Usuario realiza reservas.
- Sala contiene clases.
- ClaseGimnasio es la clase padre abstracta.
- ClaseCardio, ClaseFuerza y ClaseMenteCuerpo heredan de ClaseGimnasio.
- Reserva une Usuario con ClaseGimnasio.

**Notas para hablar:**
La parte mas importante del modelo es la herencia. ClaseGimnasio es abstracta y las clases concretas heredan de ella. Use JOINED porque cada tipo de clase tiene campos propios y asi la base de datos queda mas normalizada.

## Diapositiva 6 - Arquitectura tecnica

**Spring Boot organiza la aplicacion por capas**

- Controllers: reciben las peticiones HTTP.
- DTOs: validan y transportan datos.
- Services: contienen la logica de negocio.
- Repositories: acceden a MySQL con Spring Data JPA.
- Security: gestiona JWT y roles.

**Notas para hablar:**
La aplicacion esta separada por capas para mantener el codigo limpio. Los controladores no hacen toda la logica, sino que delegan en servicios. Los repositorios se encargan de hablar con la base de datos.

## Diapositiva 7 - Reto tecnico

**El reto principal fue combinar JWT, roles y relaciones JPA**

- Proteger rutas segun el rol del usuario.
- Evitar errores de serializacion con relaciones lazy.
- Devolver DTOs en lugar de entidades completas.
- Mantener reglas de negocio en los servicios.

**Notas para hablar:**
El mayor reto fue hacer que seguridad y JPA funcionaran bien juntas. Al principio algunas relaciones lazy podian causar errores al devolver JSON. Lo solucione usando DTOs de respuesta y controlando mejor que informacion se envia al cliente.

## Diapositiva 8 - Error y aprendizaje

**Aprendi a separar configuracion, seguridad y salida de datos**

- No subir contrasenas reales a GitHub.
- Documentar `TU_PASSWORD` para cada entorno.
- Usar DTOs para respuestas limpias.
- Probar rutas desde navegador y ejemplos HTTP.

**Notas para hablar:**
Uno de los aprendizajes fue no dejar datos sensibles en el repositorio. Tambien aprendi que devolver directamente entidades JPA no siempre es buena idea, especialmente cuando hay relaciones entre tablas.

## Diapositiva 9 - Demo y cierre

**DEMO**

- Aplicacion local: http://localhost:8080/
- GitHub: https://github.com/juangarcia15525/FitReserve

**Gracias**

**Notas para hablar:**
Ahora haria la demo: abriria la pantalla principal, mostraria salas y clases, iniciaria sesion como admin o member y haria una reserva. Gracias por escuchar.
