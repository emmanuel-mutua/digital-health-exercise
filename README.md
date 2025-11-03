# Digital Health

Minimal Spring Boot service to manage Patients and Encounters.  
Goal: simple, production-minded REST API with JPA/Hibernate (Postgres), validation, clear errors and a small test suite.

## Quick overview
- Java 17, Maven
- Spring Boot (Web, JPA, Validation), Postgres supported via `docker-compose`)
- API documented with Swagger/OpenAPI
- Layered structure: controller → service → repository
- Default port: `8080`

## Requirements
- Java 17+
- Maven 3.6+
- Docker & Docker Compose (for Postgres)

## DB
![img.png](IMAGES/DB.png)


### SWAGGER / OPENAPI
![img.png](IMAGES/swagger.png)

## Getting Started
1. Clone the repository:
   ```bash
   git clone 
   ```


### Build Services with Docker Compose:
```bash
 docker-compose up --build -d
```

### Run service:
```bash
 docker compose up
```

Stop and remove volumes:
```bash
 docker-compose down -v
```