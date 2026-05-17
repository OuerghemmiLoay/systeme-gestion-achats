# Système de Gestion des Achats et Fournisseurs

A full-stack web application for managing suppliers, purchase orders, and procurement analytics. Built with a Spring Boot 3.5.14 REST API and a simple Angular frontend.

## 📋 Quick Links

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Setup](#setup)
- [API Documentation](#api-documentation)
- [Frontend](#frontend)
- [Architecture](#architecture)

---

## ✨ Features

### Core Features
- ✅ **Supplier Management** - CRUD operations with email validation
- ✅ **Purchase Orders** - Complete lifecycle from PENDING → CONFIRMED → SHIPPED → DELIVERED
- ✅ **Supplier Evaluation** - Automated rating based on quality, delivery time, and cost
- ✅ **Offer Comparison** - Compare prices and quality metrics across suppliers
- ✅ **API Documentation** - Swagger/OpenAPI 3.x integration
- ✅ **Angular UI** - Simple dashboard and module pages for the main business flows

---

## 🛠 Tech Stack

| Component | Version | Purpose |
|-----------|---------|---------|
| **Java** | 17+ | Programming language |
| **Spring Boot** | 3.5.14 | Web framework & REST API |
| **Spring Data JPA** | Latest | ORM abstraction |
| **Maven** | 3.6+ | Build & dependency management |
| **Angular** | 17+ | Frontend UI |
| **Node.js** | 20+ | Frontend build/runtime |
| **MySQL** | 8.0+ | Database |
| **Swagger/OpenAPI** | 3.x | API documentation |
| **Docker** | Latest | Containerization |

---

## 📁 Project Structure

```
Systeme_de_gestion/
├── backend/                      # Spring Boot API
├── frontend/                     # Angular UI
├── docker-compose.yml           # Container orchestration
└── README.md                     # This file
```

---

## 🚀 Setup

### Prerequisites
- Docker Desktop or another running Docker Engine with Docker Compose v2
- Java 17 or higher for local backend builds
- Maven 3.6+ for local backend builds
- Node.js 20+ for local frontend builds
- MySQL 8.0+ if you want to run services outside Docker

### Quick Start

The deployment-ready path is Docker-based. It builds the Spring Boot API, the Angular frontend, and MySQL together with the service overrides already defined in `docker-compose.yml`.

1. **Build & Run with Docker**
```bash
# Build and start everything
docker compose up --build -d

# View logs if needed
docker compose logs -f api
```

2. **Access Application**
 - API: `http://localhost:18080/api`
- Frontend: `http://localhost:4200`
 - Swagger UI: `http://localhost:18080/api/swagger-ui.html`

### Docker Deployment

```bash
# Build and start with Docker Compose
docker compose up --build -d

# Stop services
docker compose down
```

### Docker (backend and frontend separately)

The repository contains `backend/` and `frontend/` folders. You can build and run services separately, or let `docker compose` build both contexts automatically.

Build backend and frontend manually (recommended when developing):

```bash
# build backend (from project root)
cd backend
mvn -DskipTests package

# build frontend (from project root)
cd ../frontend
npm ci
npm run build

# from project root, build and run with compose
cd ..
docker compose up --build -d
```

Or run compose directly (single-step):

```bash
docker compose up --build -d
```

You can override the default environment variables by creating a `.env` file in the project root with values like:

```env
MYSQL_ROOT_PASSWORD=root
MYSQL_DATABASE=systeme_gestion_db
MYSQL_USER=systeme_user
MYSQL_PASSWORD=systeme_password
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/systeme_gestion_db?useSSL=false&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=systeme_user
SPRING_DATASOURCE_PASSWORD=systeme_password
```

If you want to connect to the database from your host machine, use port `3307` on `localhost` while the Compose stack is running. The API is exposed on `http://localhost:18080`.

---

## 📚 API Documentation

Access interactive API documentation at: `http://localhost:8080/api/swagger-ui.html`

### Key Endpoints

**Suppliers:** `GET/POST /api/fournisseurs` | `GET /api/fournisseurs/{id}`  
**Products:** `GET/POST /api/produits` | `GET /api/produits/{id}`  
**Orders:** `GET/POST /api/commandes` | `PATCH /api/commandes/{id}/status/{status}`  
**History:** `GET/POST /api/historiques-achats` | `GET /api/historiques-achats/{id}`  
**Evaluation:** `GET /api/evaluations/supplier/{id}`

---

## 🖥 Frontend

The Angular app lives in `frontend/` and provides simple pages for:
- Dashboard
- Suppliers
- Products
- Orders
- Purchase history
- Supplier evaluations

The frontend uses the backend API at `http://localhost:8080/api`.

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