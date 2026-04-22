# bookstore-api

API REST para gestión de una librería en línea construida con Spring Boot.

## Tecnologías
- Java 17
- Spring Boot 4.0.5
- Spring Security 7 + JWT
- Spring Data JPA
- H2 Database (desarrollo)
- Lombok
- Springdoc OpenAPI (Swagger)

## Requisitos previos
- Java 17+
- Maven 3.8+ (o usar el wrapper `mvnw`)

## Configuración y ejecución local

### 1. Clonar el repositorio
```bash
git clone https://github.com/TU_USUARIO/bookstore-api.git
cd bookstore-api
```

### 2. Ejecutar la aplicación
```bash
./mvnw spring-boot:run
```
En Windows:
```bash
.\mvnw spring-boot:run
```

### 3. La API estará disponible en

http://localhost:8080/api/v1

### 4. Consola H2 (base de datos en memoria)

http://localhost:8080/api/v1/h2-console
JDBC URL: jdbc:h2:mem:bookstoredb
User: sa
Password: (vacío)

### 5. Documentación Swagger

http://localhost:8080/api/v1/swagger-ui/index.html

## Variables de entorno
| Variable | Descripción | Valor por defecto en dev |
|---|---|---|
| JWT_SECRET | Clave secreta para firmar tokens JWT | Configurada en application.yml |

## Flujo de autenticación
1. Registrar usuario: `POST /api/v1/auth/register`
2. Iniciar sesión: `POST /api/v1/auth/login` → obtienes el token
3. Usar el token en el header: `Authorization: Bearer <token>`

## Endpoints principales

### Auth (público)
| Método | Endpoint | Descripción |
|---|---|---|
| POST | /auth/register | Registrar nuevo usuario |
| POST | /auth/login | Iniciar sesión y obtener JWT |

### Books (GET público, escritura solo ADMIN)
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /books | Listar libros (paginado) |
| GET | /books/{id} | Obtener libro por ID |
| POST | /books | Crear libro |
| PUT | /books/{id} | Actualizar libro |
| DELETE | /books/{id} | Eliminar libro |

### Authors
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /authors | Listar autores |
| GET | /authors/{id} | Obtener autor por ID |
| GET | /authors/{id}/books | Libros de un autor |
| POST | /authors | Crear autor |
| PUT | /authors/{id} | Actualizar autor |
| DELETE | /authors/{id} | Eliminar autor |

### Categories
| Método | Endpoint | Descripción |
|---|---|---|
| GET | /categories | Listar categorías |
| GET | /categories/{id} | Obtener categoría por ID |
| GET | /categories/{id}/books | Libros de una categoría |
| POST | /categories | Crear categoría |
| PUT | /categories/{id} | Actualizar categoría |
| DELETE | /categories/{id} | Eliminar categoría |

### Orders (requiere autenticación)
| Método | Endpoint | Descripción |
|---|---|---|
| POST | /orders | Crear pedido |
| GET | /orders/my | Mis pedidos |
| GET | /orders | Todos los pedidos (solo ADMIN) |
| PATCH | /orders/{id}/cancel | Cancelar pedido |

## Estructura del proyecto

com.taller.bookstore
├── config/
├── controller/
├── dto/
│   ├── request/
│   └── response/
├── entity/
├── exception/
│   ├── custom/
│   └── handler/
├── mapper/
├── repository/
├── security/
└── service/
└── impl/

## Flujo Git
- `main` → producción
- `develop` → integración
- `feature/*` → desarrollo de funcionalidades

## Equipo
- Desarrollador: [Tu nombre]
- Materia: Desarrollo Empresarial
- Docente: Javier Charry