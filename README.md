# Système de Gestion des Achats et Fournisseurs

A full-stack web application for managing suppliers, purchase orders, and procurement analytics. Built with Spring Boot 3.1.0 REST API and comprehensive evaluation features.

## 📋 Quick Links

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [API Documentation](#api-documentation)
- [Architecture](#architecture)

---

## ✨ Features

### Core Features
- ✅ **Supplier Management** - CRUD operations with email validation
- ✅ **Purchase Orders** - Complete lifecycle from PENDING → CONFIRMED → SHIPPED → DELIVERED
- ✅ **Supplier Evaluation** - Automated rating based on quality, delivery time, and cost
- ✅ **Offer Comparison** - Compare prices and quality metrics across suppliers
- ✅ **API Documentation** - Swagger/OpenAPI 3.x integration

---

## 🛠 Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 11+ | Programming language |
| **Spring Boot** | 3.1.0 | Web framework & REST API |
| **Spring Data JPA** | Latest | ORM abstraction |
| **Maven** | 3.6+ | Build & dependency management |
| **MySQL** | 8.0+ | Database |
| **Swagger/OpenAPI** | 3.x | API documentation |
| **Docker** | Latest | Containerization |

---

## 📁 Project Structure

```
Systeme_de_gestion/
├── src/
│   ├── main/
│   │   ├── java/tn/itbs/
│   │   │   ├── config/           # Spring configuration
│   │   │   ├── controller/       # REST endpoints
│   │   │   ├── service/          # Business logic
│   │   │   ├── repository/       # Data access
│   │   │   ├── entity/           # JPA entities
│   │   │   ├── dto/              # DTOs
│   │   │   └── exception/        # Exception handling
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Unit tests
├── pom.xml                       # Maven configuration
├── Dockerfile                    # Docker image
├── docker-compose.yml           # Container orchestration
└── README.md                     # This file
```

---

## 🚀 Setup

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- MySQL 8.0+

### Quick Start

1. **Configure Database**
```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/systeme_gestion_db
spring.datasource.username=root
spring.datasource.password=root
```

2. **Build & Run**
```bash
# Build with Maven
mvn clean install

# Run the application
mvn spring-boot:run
```

3. **Access Application**
- API: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/api/swagger-ui.html`

### Docker Deployment

```bash
# Build and start with Docker Compose
docker-compose up -d

# Stop services
docker-compose down
```

---

## 📚 API Documentation

Access interactive API documentation at: `http://localhost:8080/api/swagger-ui.html`

### Key Endpoints

**Suppliers:** `GET/POST /api/fournisseurs` | `GET /api/fournisseurs/{id}`  
**Products:** `GET/POST /api/produits` | `GET /api/produits/{id}`  
**Orders:** `GET/POST /api/commandes` | `PATCH /api/commandes/{id}/status/{status}`  
**Evaluation:** `GET /api/evaluations/supplier/{id}`

---

## 🏗 Architecture

### Layered Architecture

```
┌─────────────────────────────────┐
│   Spring Boot REST API          │
├─────────────────────────────────┤
│   Controllers & Exception       │
├─────────────────────────────────┤
│   Service Layer (Business)      │
├─────────────────────────────────┤
│   Repository Layer (Data)       │
├─────────────────────────────────┤
│   Spring Data JPA / Hibernate   │
├─────────────────────────────────┤
│   MySQL Database                │
└─────────────────────────────────┘
```

### Design Patterns
- **DTO Pattern** - Data transfer between API and services
- **Repository Pattern** - Data access abstraction
- **Service Layer** - Business logic separation
- **Dependency Injection** - Spring managed beans
- **Global Exception Handling** - Centralized error responses

---

## 📄 License & Info

- **Created:** 2024
- **Version:** 1.0.0
- **Purpose:** Educational procurement management system