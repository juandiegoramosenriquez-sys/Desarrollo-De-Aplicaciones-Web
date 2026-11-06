# Laboratorio 15 - Sistema de Gimnasio con Microservicios

**Estudiante:** Juan Diego Ramos Enriquez · **Sección:** C
**Curso:** Desarrollo de Aplicaciones Web · Evaluación 03

Un gimnasio necesita gestionar **socios**, **planes** y **membresías**. El sistema está dividido en 3 microservicios Spring Boot independientes, cada uno con su propio puerto y su propia base de datos MySQL, más un frontend web (`gimnasio-web`) que consume las 3 APIs.

## Contenido

1. [Diagrama](#1-diagrama)
2. [Microservicios, puertos y bases de datos](#2-microservicios-puertos-y-bases-de-datos)
3. [Tecnologías](#3-tecnologías)
4. [Estructura de los proyectos](#4-estructura-de-los-proyectos)
5. [Cómo ejecutar](#5-cómo-ejecutar)
6. [Perfiles YAML](#6-perfiles-yaml)
7. [Endpoints](#7-endpoints)
8. [Validaciones y errores](#8-validaciones-y-errores)
9. [Evidencias en Postman](#9-evidencias-en-postman)
10. [Frontend](#10-frontend)
11. [Base de datos](#11-base-de-datos)
12. [Conclusiones](#12-conclusiones)
13. [Preparación para la sustentación](#13-preparación-para-la-sustentación)

---

## 1. Diagrama

![Diagrama de microservicios](imagenes/diagrama.png)

`membresia-service` **no comparte tablas** con los otros servicios: guarda solo `idSocio` e `idPlan` y, antes de registrar, consulta por REST a `socio-service` y `plan-service` para verificar que existan y estén ACTIVOS.

---

## 2. Microservicios, puertos y bases de datos

| Proyecto | Puerto | Base de datos | Tabla | Responsabilidad |
|---|---|---|---|---|
| `socio-service` | 8081 | `bd_socio` | `socios` | CRUD de socios |
| `plan-service` | 8082 | `bd_plan` | `planes` | CRUD de planes y validación del precio |
| `membresia-service` | 8083 | `bd_membresia` | `membresias` | Registro de membresías, validación de fechas y consulta por socio |
| `gimnasio-web` | 8080 | — | — | Frontend que consume las 3 APIs |

---

## 3. Tecnologías

| Herramienta | Versión / detalle |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.2 |
| Dependencias | Spring Web, Spring Data JPA, MySQL Driver, Validation, DevTools, Lombok |
| Frontend | Thymeleaf + Bootstrap 5 |
| Base de datos | MySQL de XAMPP (usuario `root`, sin contraseña) |
| IDE / pruebas | IntelliJ IDEA, Postman, SQLyog |

---

## 4. Estructura de los proyectos

Los 3 microservicios tienen la misma organización:

```
src/main/java/com/gimnasio/socioservice/
├── SocioServiceApplication.java   clase principal
├── model/        entidad JPA (@Entity) y enum de estado
├── dto/          RequestDTO (con validaciones) y ResponseDTO
├── repository/   interfaz que extiende JpaRepository
├── services/     lógica de negocio
├── controller/   endpoints REST (@RestController)
└── exception/    excepciones y GlobalExceptionHandler
```

`membresia-service` tiene además el paquete `client/` (`SocioClient` y `PlanClient`), que llama a los otros servicios con `RestClient`.

`gimnasio-web` no tiene base de datos:

```
src/main/java/com/gimnasio/gimnasioweb/
├── client/       SocioClient, PlanClient, MembresiaClient (llaman a las APIs)
├── dto/          datos que viajan entre el formulario y la API
├── controller/   @Controller que devuelven las vistas
└── exception/    muestra en pantalla los errores 400/404/503 de la API
src/main/resources/templates/
├── layout/base.html    menú y Bootstrap
├── inicio.html         resumen de los 3 servicios
├── socios/       lista.html, formulario.html
├── planes/       lista.html, formulario.html
└── membresias/   lista.html (con filtro por socio), formulario.html
```

---

## 5. Cómo ejecutar

1. Abrir **XAMPP** y presionar **Start** en **MySQL**.
2. No hace falta crear las bases de datos a mano: cada servicio crea la suya (`createDatabaseIfNotExist=true`) y sus tablas (`ddl-auto: update`) al arrancar.
3. En IntelliJ: **File → Open** y abrir cada carpeta en su propia ventana.
4. Ejecutar la clase principal de cada proyecto **en este orden**:

| Orden | Clase | Mensaje esperado |
|---|---|---|
| 1 | `SocioServiceApplication` | `Tomcat started on port 8081` |
| 2 | `PlanServiceApplication` | `Tomcat started on port 8082` |
| 3 | `MembresiaServiceApplication` | `Tomcat started on port 8083` |
| 4 | `GimnasioWebApplication` | `Tomcat started on port 8080` |

5. Probar las APIs con Postman o abrir **http://localhost:8080** para usar el frontend.

Alternativa por consola, dentro de la carpeta de cada proyecto:

```bash
mvnw.cmd spring-boot:run
```

**Si aparece "Unable to establish loopback connection":** crear la carpeta `C:\Users\Public\jtmp` y en IntelliJ ir a **Run → Edit Configurations → Modify options → Add VM options** y escribir:

```
-Djdk.net.unixdomain.tmpdir=C:/Users/Public/jtmp
```

---

## 6. Perfiles YAML

Cada servicio tiene `application.yml` (configuración común y perfil activo) y un archivo por ambiente. Por defecto se usa **dev**.

| Valor | dev | test | prod |
|---|---|---|---|
| Base de datos | `bd_socio` | `bd_socio_test` | variable `DB_URL` |
| Usuario / clave | `root` / vacío | `root` / vacío | variables `DB_USER` / `DB_PASSWORD` |
| `ddl-auto` | `update` (crea/actualiza tablas) | `create-drop` (borra al apagar) | `validate` (solo verifica) |
| `show-sql` | `true` | `true` | `false` |
| Nivel de log | DEBUG | INFO | WARN |
| Puerto | fijo | fijo | variable `PORT` |

Para cambiar de perfil: modificar `spring.profiles.active` en `application.yml`, o agregar el VM option `-Dspring.profiles.active=prod`.

---

## 7. Endpoints

### socio-service (http://localhost:8081)

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/socios` | Listar socios | 200 |
| GET | `/api/socios/{id}` | Buscar por ID | 200 / 404 |
| POST | `/api/socios` | Registrar | 201 / 400 |
| PUT | `/api/socios/{id}` | Actualizar | 200 / 400 / 404 |
| DELETE | `/api/socios/{id}` | Eliminar | 204 / 404 |

### plan-service (http://localhost:8082)

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/planes` | Listar planes | 200 |
| GET | `/api/planes/{id}` | Buscar por ID | 200 / 404 |
| POST | `/api/planes` | Registrar | 201 / 400 |
| PUT | `/api/planes/{id}` | Actualizar | 200 / 400 / 404 |
| DELETE | `/api/planes/{id}` | Eliminar | 204 / 404 |

### membresia-service (http://localhost:8083)

| Método | Ruta | Descripción | Respuesta |
|---|---|---|---|
| GET | `/api/membresias` | Listar membresías | 200 |
| GET | `/api/membresias/{id}` | Buscar por ID | 200 / 404 |
| GET | `/api/membresias/socio/{idSocio}` | **Consultar membresías de un socio** | 200 / 404 |
| POST | `/api/membresias` | Registrar | 201 / 400 / 404 / 503 |
| PUT | `/api/membresias/{id}` | Actualizar | 200 / 400 / 404 |
| DELETE | `/api/membresias/{id}` | Eliminar | 204 / 404 |

---

## 8. Validaciones y errores

| Servicio | Regla | Respuesta si falla |
|---|---|---|
| Socio | DNI de 8 dígitos, nombres/apellidos/correo obligatorios, correo válido, teléfono de 9 dígitos que empieza con 9 | 400 |
| Socio | No se repite el DNI | 400 |
| Plan | **Precio mayor a 0** y con máximo 2 decimales | 400 |
| Plan | Duración entre 1 y 24 meses, nombre único | 400 |
| Membresía | **fechaFin posterior a fechaInicio** | 400 |
| Membresía | El socio y el plan deben existir | 404 |
| Membresía | El socio y el plan deben estar ACTIVOS | 400 |
| Membresía | socio-service o plan-service apagado | 503 |
| Todos | ID inexistente | 404 |

Formato de error (igual en los 3 servicios):

```json
{
  "fecha": "2026-11-05T22:19:32",
  "status": 400,
  "mensaje": "Los datos enviados no son validos",
  "ruta": "/api/socios",
  "errores": {
    "dni": "El DNI debe tener 8 digitos",
    "correo": "El correo no tiene un formato valido"
  }
}
```

---

## 9. Evidencias en Postman

### Socios (puerto 8081)

**1. Registrar socio → 201 Created**
`POST http://localhost:8081/api/socios`

```json
{
  "dni": "72345678",
  "nombres": "Juan Diego",
  "apellidos": "Ramos Enriquez",
  "correo": "juan@gmail.com",
  "telefono": "987654321",
  "estado": "ACTIVO"
}
```

![Registrar socio](imagenes/01-socio-registrar.png)

**2. Listar socios → 200 OK**
`GET http://localhost:8081/api/socios`

![Listar socios](imagenes/02-socio-listar.png)

**3. Buscar socio por id → 200 OK**
`GET http://localhost:8081/api/socios/2`

![Buscar socio](imagenes/03-socio-buscar.png)

**4. Actualizar socio → 200 OK**
`PUT http://localhost:8081/api/socios/2`

![Actualizar socio](imagenes/04-socio-actualizar.png)

**5. ERROR: datos inválidos → 400 Bad Request**
`POST http://localhost:8081/api/socios`

```json
{
  "dni": "123",
  "nombres": "",
  "apellidos": "Perez",
  "correo": "malo",
  "estado": "ACTIVO"
}
```

![Socio con datos inválidos](imagenes/05-socio-datos-invalidos.png)

**6. ERROR: DNI repetido → 400 Bad Request**
`POST http://localhost:8081/api/socios`

![DNI repetido](imagenes/06-socio-dni-repetido.png)

**7. ERROR: socio inexistente → 404 Not Found**
`GET http://localhost:8081/api/socios/999`

![Socio inexistente](imagenes/07-socio-inexistente.png)

### Planes (puerto 8082)

**8. Registrar plan → 201 Created**
`POST http://localhost:8082/api/planes`

```json
{
  "nombre": "Plan Mensual",
  "descripcion": "Sala de maquinas",
  "precio": 120.00,
  "duracionMeses": 1,
  "estado": "ACTIVO"
}
```

![Registrar plan](imagenes/08-plan-registrar.png)

**9. Listar planes → 200 OK**
`GET http://localhost:8082/api/planes`

![Listar planes](imagenes/09-plan-listar.png)

**10. Buscar plan por id → 200 OK**
`GET http://localhost:8082/api/planes/2`

![Buscar plan](imagenes/10-plan-buscar.png)

**11. Actualizar plan → 200 OK**
`PUT http://localhost:8082/api/planes/2`

![Actualizar plan](imagenes/11-plan-actualizar.png)

**12. ERROR: precio negativo → 400 Bad Request**
`POST http://localhost:8082/api/planes`

```json
{
  "nombre": "Plan Malo",
  "precio": -50,
  "duracionMeses": 1,
  "estado": "ACTIVO"
}
```

![Precio negativo](imagenes/12-plan-precio-negativo.png)

**13. ERROR: plan inexistente → 404 Not Found**
`GET http://localhost:8082/api/planes/999`

![Plan inexistente](imagenes/13-plan-inexistente.png)

### Membresías (puerto 8083)

**14. Registrar membresía → 201 Created**
`POST http://localhost:8083/api/membresias`

```json
{
  "idSocio": 2,
  "idPlan": 2,
  "fechaInicio": "2026-11-01",
  "fechaFin": "2026-12-01",
  "estado": "ACTIVA"
}
```

![Registrar membresía](imagenes/14-membresia-registrar.png)

**15. Listar membresías → 200 OK**
`GET http://localhost:8083/api/membresias`

![Listar membresías](imagenes/15-membresia-listar.png)

**16. Buscar membresía por id → 200 OK**
`GET http://localhost:8083/api/membresias/2`

![Buscar membresía](imagenes/16-membresia-buscar.png)

**17. Actualizar membresía a CANCELADA → 200 OK**
`PUT http://localhost:8083/api/membresias/2`

![Actualizar membresía](imagenes/17-membresia-actualizar.png)

**18. ERROR: fecha fin antes que fecha inicio → 400 Bad Request**
`POST http://localhost:8083/api/membresias`

```json
{
  "idSocio": 2,
  "idPlan": 2,
  "fechaInicio": "2026-12-01",
  "fechaFin": "2026-11-01",
  "estado": "ACTIVA"
}
```

![Fechas inválidas](imagenes/18-membresia-fechas-invalidas.png)

**19. ERROR: socio inexistente → 404 Not Found**
`POST http://localhost:8083/api/membresias` con `"idSocio": 999`

![Membresía con socio inexistente](imagenes/19-membresia-socio-inexistente.png)

**20. ERROR: membresía inexistente → 404 Not Found**
`GET http://localhost:8083/api/membresias/999`

![Membresía inexistente](imagenes/20-membresia-inexistente.png)

**21. ERROR: socio-service apagado → 503 Service Unavailable**
`GET http://localhost:8083/api/membresias/socio/999` con socio-service detenido

![Servicio apagado](imagenes/21-membresia-servicio-apagado.png)

### Eliminar

| # | Petición | Resultado |
|---|---|---|
| 22 | `DELETE http://localhost:8083/api/membresias/2` | 204 No Content |
| 23 | `DELETE http://localhost:8082/api/planes/2` | 204 No Content |
| 24 | `DELETE http://localhost:8081/api/socios/2` | 204 No Content |
| 25 | `DELETE http://localhost:8081/api/socios/2` (otra vez) | 404 Not Found |

**204:** se eliminó correctamente. **404:** no existe ese ID para eliminar.

![Eliminar membresía](imagenes/22-eliminar-membresia.png)
![Eliminar plan](imagenes/23-eliminar-plan.png)
![Eliminar socio](imagenes/24-eliminar-socio.png)
![Eliminar socio inexistente](imagenes/25-eliminar-socio-inexistente.png)

---

## 10. Frontend

`gimnasio-web` (http://localhost:8080) muestra las 3 APIs en pantallas. No guarda datos: cada formulario envía el JSON al microservicio correspondiente.

### Inicio

![Inicio](imagenes/web-07-inicio.png)

### Nuevo socio

![Nuevo socio](imagenes/web-01-nuevo-socio.png)
![Lista de socios](imagenes/web-02-lista-socios.png)

### Nuevo plan

![Nuevo plan](imagenes/web-03-nuevo-plan.png)
![Lista de planes](imagenes/web-04-lista-planes.png)

### Nueva membresía

![Nueva membresía](imagenes/web-05-nueva-membresia.png)
![Lista de membresías](imagenes/web-06-lista-membresias.png)

---

## 11. Base de datos

Datos guardados por el frontend, consultados en SQLyog. Cada servicio tiene su propia base de datos.

**bd_membresia → membresias**

![Tabla membresias](imagenes/bd-01-membresias.png)

**bd_socio → socios**

![Tabla socios](imagenes/bd-02-socios.png)

**bd_plan → planes**

![Tabla planes](imagenes/bd-03-planes.png)

---

## 12. Conclusiones

1. **Separar el sistema en microservicios facilita el mantenimiento.** Socio, Plan y Membresía son proyectos independientes, cada uno con su propio puerto y su propia base de datos, así que un cambio en uno no afecta a los demás.
2. **Entre microservicios se comparten IDs, no tablas.** Membresía solo guarda `idSocio` e `idPlan` y, para verificar que existen, consulta por REST a los otros servicios.
3. **Spring Data JPA genera las consultas automáticamente.** Basta con declarar `findByIdSocio` en el repositorio para obtener las membresías de un socio, sin escribir SQL.
4. **Los DTO y las validaciones protegen la API.** Con `@Valid`, `@NotBlank` y `@DecimalMin` los datos incorrectos se rechazan antes de llegar a la base de datos, y el `GlobalExceptionHandler` devuelve el error HTTP correcto (400, 404 o 503).
5. **Los perfiles YAML permiten usar el mismo código en distintos ambientes.** En dev se crean las tablas y se muestra el SQL, en test la base se borra al terminar y en prod las credenciales vienen de variables de entorno.

### Dificultad encontrada

Al ejecutar los servicios, Tomcat no arrancaba y mostraba `Unable to establish loopback connection`. Era un problema de Java en Windows con la carpeta temporal del usuario y se solucionó con el VM option `-Djdk.net.unixdomain.tmpdir=C:/Users/Public/jtmp`.

---

## 13. Preparación para la sustentación

**¿Por qué separé el sistema en estos servicios?**
Porque cada uno tiene una responsabilidad distinta: Socio administra personas, Plan administra la oferta y los precios, y Membresía une a un socio con un plan en un período de tiempo. Cada uno tiene su propia base de datos y se puede ejecutar por separado; si uno falla, los demás siguen funcionando.

**¿Cómo persisto los datos?**
Con Spring Data JPA e Hibernate sobre MySQL. La clase `@Entity` se mapea a una tabla, el repositorio extiende `JpaRepository` (que ya trae `save`, `findById`, `findAll` y `delete`) y la conexión está en `application-dev.yml`. Hibernate crea las tablas con `ddl-auto: update`.

**¿Qué ocurre cuando la API recibe información inválida?**
`@Valid` valida el DTO; si algo falla, el `GlobalExceptionHandler` responde **400** con el mensaje de cada campo. Si el ID no existe responde **404**, si las fechas están al revés **400**, y si socio-service o plan-service están apagados **503**. Nunca se guarda un dato inválido.

### Recorrido del código (ejemplo con Socio)

1. `model/Socio.java` → entidad, tabla `socios`, `@Id`, `@Column`, `@Enumerated`.
2. `dto/SocioRequestDTO.java` → validaciones de entrada.
3. `repository/SocioRepository.java` → `JpaRepository` + `existsByDni`.
4. `services/SocioService.java` → regla de DNI único y conversión entidad ↔ DTO.
5. `controller/SocioController.java` → `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`.
6. `exception/GlobalExceptionHandler.java` → convierte excepciones en respuestas HTTP.

### Cambios en vivo que pueden pedir

| Pedido | Dónde se cambia |
|---|---|
| Agregar un campo (ej. `fechaNacimiento` en Socio) | `Socio` → `SocioRequestDTO` → `SocioResponseDTO` → `copiarDatos` y `convertirAResponse` en el service |
| Nueva validación (ej. precio máximo 1000) | `@DecimalMax` en `PlanRequestDTO` |
| Nueva consulta (ej. membresías por estado) | `findByEstado` en el repositorio → método en el service → `@GetMapping` en el controller |
| Cambiar el puerto | `server.port` en `application.yml` |
