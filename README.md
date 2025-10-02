# Product Management Service

A Spring Boot 3.4.6 application for managing products, built with Java 21, PostgreSQL, Hibernate, and Querydsl. The
service includes REST APIs, Swagger documentation, and Docker support for easy setup.

## Features

- Create new products and store them in the database
- Retrieve a specific product by ID
- List all products
- Conversion of prices from **EUR → USD** using the HNB API
- API documentation available via **Swagger UI**
- Docker Compose for (includes PostgreSQL)

---

## Table of Contents

- [Getting Started](#getting-started)
- [Build and Run](#build-and-run)
- [Docker](#docker)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Configuration](#configuration)
- [Technologies](#technologies)

---

## Getting Started

### Clone the repository

```bash
git clone https://github.com/kprusina/product-management-service.git
cd product-management-service
```

## Build and run

### Build the app

```bash
mvn clean package
```

### Run the application

```bash
mvn spring-boot:run
```

The service will start on http://localhost:8080.

## Docker

You can run the service with PostgreSQL using Docker Compose:

```bash
docker-compose up --build
```

This will start two services:
app: Spring Boot application on port 8080
db: PostgreSQL database on port 5432

### Environment Variables

Application :

```yaml
SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/PRODUCT_DB
SPRING_DATASOURCE_USERNAME: postgres
SPRING_DATASOURCE_PASSWORD: postgres
```

Database:

```yaml
POSTGRES_USER: postgres
POSTGRES_PASSWORD: postgres
POSTGRES_DB: PRODUCT_DB
```

## API Documentation

The service exposes an OpenAPI (Swagger) specification.

### Swagger UI

Access the Swagger UI:
http://localhost:8080/swagger-ui/index.html

You can use it to:
Explore endpoints
View request/response schemas
Test API calls from the browser
OpenAPI JSON
The raw OpenAPI specification is available at:
http://localhost:8080/v3/api-docs

## Testing

- **Unit tests**: service and controller layers are tested using **JUnit 5** and **Mockito**, with dependencies mocked
  for isolated tests
- **Repository tests**: tested using **`@DataJpaTest`** with an **in-memory database (H2)** to verify saving and
  retrieving products
- **Controller tests**: use **MockMvc** to verify REST endpoints return the expected responses

### Run Tests

```bash
mvn test
```

### Test Structure

```swift
src/test/java/com/github/kprusina/feature/product/ProductControllerTest.java → controller tests using MockMvc
src/test/java/com/github/kprusina/feature/product/ProductServiceTest.java    → service layer unit tests with Mockito
src/test/java/com/github/kprusina/feature/product/ProductRepositoryTest.java → repository tests using @DataJpaTest with H2
```

### Configuration

```properties
spring.application.name=product-management-service
# PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/PRODUCT_DB
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
# Hibernate / JPA
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
# Flyway
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true
```

## Technologies

Java 21
Spring Boot 3.4.6
Spring Data JPA (Hibernate)
PostgreSQL
Querydsl
Swagger / OpenAPI (springdoc)
Maven
Docker / Docker Compose
JUnit 5, Mockito, Testcontainers
