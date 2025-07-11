# Attendance System API

A comprehensive Human Resources Management System built with Spring Boot, featuring employee attendance tracking, authentication, robust authorization, and centralized error handling.

## Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **Attendance Management**: Employee check-in/check-out tracking with fingerprint integration
- **RabbitMQ Integration**: Asynchronous message processing for attendance data
- **Database**: PostgreSQL with Flyway migrations
- **API Documentation**: Complete Swagger/OpenAPI documentation with annotated DTOs and endpoints
- **Centralized Error Handling**: Consistent error responses for invalid path variables and unmapped routes

## Technology Stack

- **Backend**: Spring Boot 3.5.0
- **Database**: PostgreSQL
- **Message Queue**: RabbitMQ
- **Security**: Spring Security with JWT (stateless, filter-based)
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Java Version**: 21
- **Flyway**: Database migrations
- **ModelMapper**: DTO mapping

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- PostgreSQL
- RabbitMQ

### Installation

1. Clone the repository
2. Configure your database connection in `application.properties`
3. Configure RabbitMQ connection in `application.properties`
4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

## API Documentation

### Swagger UI

Once the application is running, access the interactive API documentation at:

- **Swagger UI**: http://localhost:8080/api/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api/api-docs

> **Note:** If Swagger UI does not load, ensure your security configuration allows unauthenticated access to `/swagger-ui.html`, `/swagger-ui/**`, `/v3/api-docs/**`, and `/webjars/**`. Example:
>
> ```java
> .authorizeHttpRequests(auth -> auth
>   .requestMatchers(
>       "/swagger-ui.html",
>       "/swagger-ui/**",
>       "/v3/api-docs/**",
>       "/webjars/**"
>   ).permitAll()
>   .anyRequest().authenticated()
> )
> ```

### Available Endpoints

#### Authentication (`/api/auth`) (all endpoints prefixed with `/api`)
- `POST /auth/login` - User login
- `GET /auth/refresh-token` - Refresh access token
- `POST /auth/signup` - Create new user (Admin only)
- `PATCH /auth/change-password` - Change password (Authenticated users)

#### Attendance Management (`/api/attendance`) (all endpoints prefixed with `/api`)
- `POST /api/attendance/check-in-out` - Register check-in or check-out
- `GET /api/attendance` - Get attendance logs (Paginated)

## Security & Error Handling

- All endpoints (except Swagger and authentication) require a valid JWT token.
- Roles: **USER** (self-service), **ADMIN** (full access).
- Stateless session management, JWT filter intercepts requests before authentication.
- **Centralized error handling:**
  - Invalid path variables (e.g., `/api/jobs/abc` when `id` is expected) return:
    ```json
    {"error": "Path variable 'id' must be a number, but value 'abc' is invalid.", "status": "BAD_REQUEST", "code": 400}
    ```
  - Unmapped routes (e.g., `/api/unknown`) return `401 Unauthorized` (for security) instead of 404.
  - Custom error controllers for domain-specific exceptions (e.g., attendance not found).

## Configuration

Key configuration properties in `application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/database
spring.datasource.username=postgres
spring.datasource.password=postgres

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# JWT
application.security.jwt.secret=1234
application.security.jwt.expiration=3600000

# Attendance
application.attendance.check-in-time=08:30
application.attendance.check-out-time=16:30

# Swagger
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/api-docs
```

## Data Models

### Request DTOs

All DTOs are annotated with Swagger `@Schema` for automatic documentation and Jakarta Bean Validation for input validation.

- `AttendaceRequestDto`: For attendance check-in/check-out
- `LoginRequest`: For user authentication
- `SignUpRequestDto`: For user registration
- `ChangePasswordRequestDto`: For password changes

### Response DTOs

- `AuthenticationResponseDto`: JWT token and refresh token
- `BaseResponseDto`: Generic response wrapper
- `AttendanceLogResponseDto`: Attendance log data
- `ErrorResponse`: Standardized error response for invalid requests

## Development

### Project Structure

```
src/main/java/com/dakson/hr/
├── app/
│   ├── attendance/       # Attendance management
│   ├── jobs/             # Job and job history management
│   └── location/         # Department and location management
├── core/
│   ├── authentication/   # JWT authentication, security config, filters
│   ├── authorization/    # Role management
│   └── user/             # User management
├── common/
│   ├── error_controller/ # Global error handlers
│   └── model/            # Shared DTOs and response models
```

### Running Tests

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License. 
