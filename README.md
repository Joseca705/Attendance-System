# HR Management System API

A comprehensive Human Resources Management System built with Spring Boot, featuring employee attendance tracking, authentication, and authorization.

## Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **Attendance Management**: Employee check-in/check-out tracking with fingerprint integration
- **RabbitMQ Integration**: Asynchronous message processing for attendance data
- **Database**: PostgreSQL with Flyway migrations
- **API Documentation**: Complete Swagger/OpenAPI documentation

## Technology Stack

- **Backend**: Spring Boot 3.5.0
- **Database**: PostgreSQL
- **Message Queue**: RabbitMQ
- **Security**: Spring Security with JWT
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Build Tool**: Maven
- **Java Version**: 21

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

Once the application is running, you can access the interactive API documentation at:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs

### Available Endpoints

#### Authentication (`/auth`)
- `POST /auth/login` - User login
- `GET /auth/refresh-token` - Refresh access token
- `POST /auth/signup` - Create new user (Admin only)
- `PATCH /auth/change-password` - Change password (Authenticated users)

#### Attendance Management (`/api/attendance`)
- `POST /api/attendance/check-in-out` - Register check-in or check-out
- `GET /api/attendance` - Get attendance logs (Paginated)

## Security

The API uses JWT-based authentication with the following roles:
- **USER**: Can access their own attendance data and change password
- **ADMIN**: Can create new users and access all features

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

- `AttendaceRequestDto`: For attendance check-in/check-out
- `LoginRequest`: For user authentication
- `SignUpRequestDto`: For user registration
- `ChangePasswordRequestDto`: For password changes

### Response DTOs

- `AuthenticationResponseDto`: JWT token and refresh token
- `BaseResponseDto`: Generic response wrapper
- `AttendanceLogResponseDto`: Attendance log data

## Development

### Project Structure

```
src/main/java/com/dakson/hr/
├── app/
│   └── attendance/          # Attendance management
│       ├── api/            # Controllers and DTOs
│       ├── domain/         # Entities and repositories
│       └── infrastructure/ # Services and configuration
├── core/
│   ├── authentication/     # JWT authentication
│   ├── authorization/      # Role management
│   └── user/              # User management
└── common/                # Shared utilities and models
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