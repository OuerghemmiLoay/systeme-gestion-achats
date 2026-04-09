# Système de Gestion des Achats et Fournisseurs

A complete full-stack web application for managing suppliers, purchase orders, and procurement analytics. Built with Spring Boot REST API and Angular front-end with comprehensive evaluation and offer comparison features.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Docker Deployment](#docker-deployment)
- [API Documentation](#api-documentation)
- [Usage Guide](#usage-guide)
- [Architecture](#architecture)
- [Git Workflow](#git-workflow)
- [Deployment](#deployment)

---

## 🎯 Overview

This application provides a complete solution for managing suppliers and purchase orders with advanced features including:

- **Supplier Management**: Create, update, and manage supplier information
- **Purchase Orders**: Complete order lifecycle management with status tracking
- **Supplier Evaluation**: Automated rating system based on quality, delivery time, and cost
- **Offer Comparison**: Compare prices and quality metrics across multiple suppliers
- **Delivery Tracking**: Track order status from pending to delivered
- **Analytics Dashboard**: View performance metrics and supplier rankings

---

## ✨ Features

### Core CRUD Operations
- ✅ **Suppliers (Fournisseurs)**: Full CRUD with email validation
- ✅ **Products (Produits)**: Manage product catalog with pricing
- ✅ **Purchase Orders (Commandes)**: Create and manage orders
- ✅ **Order Line Items (Lignes Commande)**: Add/remove items from orders
- ✅ **Purchase History (Historique Achats)**: Track all historical purchases

### Advanced Features
- 🔍 **Supplier Evaluation**: 
  - Quality service rating (1-5)
  - Delivery time analysis
  - On-time delivery rate calculation
  - Cost efficiency metrics
  - Overall rating combining all factors

- 💰 **Offer Comparison**:
  - Compare prices across suppliers
  - Quality metrics comparison
  - Delivery time comparison
  - Automatic best offer recommendation
  - Scoring system for decision support

- 📦 **Order Management**:
  - Restricted workflow (PENDING → CONFIRMED → SHIPPED → DELIVERED)
  - Status validation to prevent invalid transitions
  - Automatic delivery recording in history
  - Supplier rating updates on delivery

- 📊 **Analytics**:
  - Supplier performance dashboard
  - Order status filtering and tracking
  - Delivery performance trends
  - Cost analysis

### Non-Functional Features
- ✅ API Documentation with Swagger/OpenAPI
- ✅ Input validation with Spring Validator
- ✅ Global exception handling
- ✅ CORS support for front-end
- ✅ Docker containerization
- ✅ Transactional consistency
- ✅ Clean architecture with separation of concerns

---

## 🛠 Technology Stack

### Back-end
| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17 LTS | Programming language |
| Spring Boot | 4.0.5 | Web framework |
| Spring Data JPA | Latest | Data access layer |
| Hibernate | 8.x | ORM framework |
| MySQL | 8.0 | Relational database |
| Swagger/OpenAPI | 3.0 | API documentation |
| Lombok | 1.18.x | Boilerplate reduction |
| JUnit 5 | Latest | Unit testing |

### Front-end
| Technology | Version | Purpose |
|-----------|---------|---------|
| Angular | 18+ | Web framework |
| TypeScript | 5+ | Programming language |
| RxJS | 7+ | Reactive programming |
| Angular Material | Latest | UI components (optional) |
| Bootstrap | 5+ | Styling (alternative) |
| Jasmine | Latest | Unit testing |
| Karma | Latest | Test runner |

### DevOps & Deployment
| Technology | Purpose |
|-----------|---------|
| Docker | Containerization |
| Docker Compose | Container orchestration |
| Gradle | Build automation |
| Git | Version control |

---

## 📁 Project Structure

```
Systeme_de_gestion/
├── src/
│   ├── main/
│   │   ├── java/tn/itbs/note/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── WebConfig.java
│   │   │   │   └── SwaggerConfig.java
│   │   │   ├── controller/          # REST endpoints
│   │   │   │   ├── FournisseurController.java
│   │   │   │   ├── CommandeAchatController.java
│   │   │   │   ├── LigneCommandeAchatController.java
│   │   │   │   ├── ProduitController.java
│   │   │   │   └── EvaluationController.java
│   │   │   ├── service/             # Business logic
│   │   │   │   ├── FournisseurService.java
│   │   │   │   ├── CommandeAchatService.java
│   │   │   │   ├── LigneCommandeAchatService.java
│   │   │   │   ├── ProduitService.java
│   │   │   │   └── EvaluationService.java
│   │   │   ├── repository/          # Data access layer
│   │   │   │   ├── FournisseurRepository.java
│   │   │   │   ├── CommandeAchatRepository.java
│   │   │   │   ├── LigneCommandeAchatRepository.java
│   │   │   │   ├── ProduitRepository.java
│   │   │   │   └── HistoriqueAchatsRepository.java
│   │   │   ├── entity/              # JPA entities
│   │   │   │   ├── Fournisseur.java
│   │   │   │   ├── CommandeAchat.java
│   │   │   │   ├── LigneCommandeAchat.java
│   │   │   │   ├── Produit.java
│   │   │   │   └── HistoriqueAchats.java
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   │   ├── FournisseurDTO.java
│   │   │   │   ├── CommandeAchatDTO.java
│   │   │   │   ├── LigneCommandeAchatDTO.java
│   │   │   │   ├── ProduitDTO.java
│   │   │   │   ├── HistoriqueAchatsDTO.java
│   │   │   │   ├── EvaluationDTO.java
│   │   │   │   ├── OffreProduitDTO.java
│   │   │   │   └── ApiResponse.java
│   │   │   ├── exception/           # Exception handling
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   ├── BusinessException.java
│   │   │   │   ├── ErrorResponse.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── SystemeDeGestionApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                        # Unit and integration tests
├── frontend/                        # Angular application
├── gradle/                          # Gradle wrapper
├── build.gradle                     # Build configuration
├── settings.gradle
├── Dockerfile                       # Docker image definition
├── docker-compose.yml              # Container orchestration
├── .dockerignore
├── ANGULAR_SETUP.md                # Angular setup guide
├── README.md                        # This file
└── HELP.md                         # Spring Boot help

```

---

## 🚀 Installation & Setup

### Prerequisites
- Java 17 or higher
- MySQL 8.0+
- Gradle 7.0+
- Git

### Step 1: Clone Repository

```bash
git clone <repository-url>
cd Systeme_de_gestion
```

### Step 2: Configure Database

Create a MySQL database:

```sql
CREATE DATABASE systeme_gestion_db;
CREATE USER 'systeme_user'@'localhost' IDENTIFIED BY 'systeme_password';
GRANT ALL PRIVILEGES ON systeme_gestion_db.* TO 'systeme_user'@'localhost';
FLUSH PRIVILEGES;
```

Or update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/systeme_gestion_db
spring.datasource.username=root
spring.datasource.password=root
```

### Step 3: Build Back-end

```bash
# Using Gradle
./gradlew clean build

# Or on Windows
gradlew.bat clean build
```

### Step 4: Run Back-end

```bash
# Using Gradle
./gradlew bootRun

# Or run the built JAR
java -jar build/libs/Systeme_de_gestion-0.0.1-SNAPSHOT.jar
```

The API will be available at: `http://localhost:8080/api`

### Step 5: Setup Front-end

See [ANGULAR_SETUP.md](./ANGULAR_SETUP.md) for detailed Angular setup instructions.

```bash
# Navigate to frontend directory
cd frontend

# Install dependencies
npm install

# Run development server
ng serve --open
```

The application will be available at: `http://localhost:4200`

---

## 🐳 Docker Deployment

### Prerequisites
- Docker Desktop installed and running
- Docker Compose

### Build and Run with Docker Compose

```bash
# Navigate to project root
cd Systeme_de_gestion

# Build and start all services
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f api
docker-compose logs -f mysql

# Stop services
docker-compose down

# Clean up (including volumes)
docker-compose down -v
```

### Services Deployed

1. **MySQL Database**
   - Host: `localhost:3306`
   - Database: `systeme_gestion_db`
   - User: `systeme_user`
   - Password: `systeme_password`

2. **Spring Boot API**
   - Host: `http://localhost:8080/api`
   - Swagger UI: `http://localhost:8080/api/swagger-ui.html`
   - Health check: `http://localhost:8080/api/actuator/health` (if enabled)

### Docker Compose Configuration

The `docker-compose.yml` includes:
- Multi-stage Gradle build for optimized image
- MySQL 8.0 with persistent volume
- Health checks for automatic recovery
- Network isolation
- Environment variable configuration

### Build Individual Docker Image

```bash
# Build Spring Boot image
docker build -t systeme-gestion-api:latest .

# Run container
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/systeme_gestion_db \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=root \
  systeme-gestion-api:latest
```

---

## 📚 API Documentation

### Swagger/OpenAPI

Access interactive API documentation at:

```
http://localhost:8080/api/swagger-ui.html
```

### API Base URL

```
http://localhost:8080/api
```

### Main Endpoints

#### Suppliers (Fournisseurs)
```
GET    /fournisseurs              - Get all suppliers
GET    /fournisseurs/{id}         - Get supplier by ID
GET    /fournisseurs/ranked       - Get suppliers ranked by rating
GET    /fournisseurs/high-quality - Get high-quality suppliers (quality >= 4)
GET    /fournisseurs/search/{nom} - Search supplier by name
POST   /fournisseurs              - Create new supplier
PUT    /fournisseurs/{id}         - Update supplier
DELETE /fournisseurs/{id}         - Delete supplier
```

#### Products (Produits)
```
GET    /produits                  - Get all products
GET    /produits/{id}             - Get product by ID
GET    /produits/search/{nom}     - Search products by name
POST   /produits                  - Create new product
PUT    /produits/{id}             - Update product
DELETE /produits/{id}             - Delete product
```

#### Purchase Orders (Commandes Achat)
```
GET    /commandes                 - Get all orders
GET    /commandes/{id}            - Get order by ID
GET    /commandes/fournisseur/{id} - Get orders by supplier
GET    /commandes/statut/{statut} - Get orders by status
POST   /commandes                 - Create new order
PUT    /commandes/{id}            - Update order
PATCH  /commandes/{id}/status/{status} - Update order status (with delivery tracking)
DELETE /commandes/{id}            - Delete order (only if PENDING)
```

#### Order Line Items (Lignes Commande)
```
GET    /lignes-commande/commande/{id} - Get line items for order
GET    /lignes-commande/{id}          - Get line item by ID
POST   /lignes-commande               - Create new line item
PUT    /lignes-commande/{id}          - Update line item (only if order PENDING)
DELETE /lignes-commande/{id}          - Delete line item (only if order PENDING)
```

#### Evaluations & Comparisons
```
GET    /evaluations/supplier/{id}      - Evaluate specific supplier
GET    /evaluations/all                - Evaluate all suppliers
GET    /evaluations/compare-offers/{nom} - Compare offers for product
```

### Example Requests

#### Create Supplier
```bash
POST /api/fournisseurs
Content-Type: application/json

{
  "nom": "Supplier ABC",
  "contact": "contact@supplierabc.com",
  "qualite_service": 4
}
```

#### Create Purchase Order
```bash
POST /api/commandes
Content-Type: application/json

{
  "fournisseurId": 1,
  "montant": 1500.00,
  "notes": "Urgent order"
}
```

#### Update Order Status
```bash
PATCH /api/commandes/1/status/CONFIRMED
```

#### Compare Offers
```bash
GET /api/evaluations/compare-offers/ProductName
```

---

## 💡 Usage Guide

### Creating a Purchase Order

1. **Create Supplier** (if not exists)
   - POST `/fournisseurs` with supplier details

2. **Create Product** (if not exists)
   - POST `/produits` with product details

3. **Create Order**
   - POST `/commandes` with supplier ID and amount

4. **Add Line Items**
   - POST `/lignes-commande` for each product in order
   - Provide order ID, product ID, quantity, and unit price

5. **Confirm Order**
   - PATCH `/commandes/{id}/status/CONFIRMED`

6. **Mark as Shipped**
   - PATCH `/commandes/{id}/status/SHIPPED`

7. **Mark as Delivered**
   - PATCH `/commandes/{id}/status/DELIVERED`
   - Automatically records in purchase history
   - Updates supplier rating

### Evaluating Suppliers

1. **Get Single Supplier Evaluation**
   ```
   GET /evaluations/supplier/{supplierId}
   ```
   Returns: Quality score, delivery time, on-time rate, cost, overall rating, recommendation

2. **Get All Suppliers Evaluated**
   ```
   GET /evaluations/all
   ```
   Returns: List of all suppliers with their evaluation metrics, ranked by overall rating

### Comparing Offers

1. **Compare Multiple Suppliers for Same Product**
   ```
   GET /evaluations/compare-offers/{productName}
   ```
   Returns: List of offers with prices, quality, delivery times, and recommended best offer

---

## 🏗 Architecture

### Layered Architecture

```
┌─────────────────────────────────────────┐
│         Angular Front-end (UI)          │ ← User Interface
├─────────────────────────────────────────┤
│         Spring Boot REST API            │ ← Presentation Layer
├─────────────────────────────────────────┤
│    Controllers (Request Handling)       │
│    └─ Global Exception Handler          │
├─────────────────────────────────────────┤
│      Service Layer (Business Logic)     │
│    ├─ FournisseurService                │
│    ├─ CommandeAchatService              │
│    ├─ LigneCommandeAchatService         │
│    ├─ ProduitService                    │
│    └─ EvaluationService                 │
├─────────────────────────────────────────┤
│    Repository Layer (Data Access)       │
│    ├─ FournisseurRepository             │
│    ├─ CommandeAchatRepository           │
│    ├─ LigneCommandeAchatRepository      │
│    ├─ ProduitRepository                 │
│    └─ HistoriqueAchatsRepository        │
├─────────────────────────────────────────┤
│   Spring Data JPA / Hibernate ORM       │ ← Persistence Layer
├─────────────────────────────────────────┤
│         MySQL Database                  │ ← Data Storage
└─────────────────────────────────────────┘
```

### Design Patterns Used

1. **DTO Pattern** - Data Transfer Objects for API boundaries
2. **Repository Pattern** - Data access abstraction
3. **Service Layer Pattern** - Business logic encapsulation
4. **Dependency Injection** - Spring managed beans
5. **Exception Handling** - Global exception handler
6. **Transactional Processing** - Database consistency

### Data Model Relationships

```
Fournisseur (1) ──────────────── (n) CommandeAchat
    │                                    │
    │ (1)                                │ (1)
    │                                    │
    └─── (n) HistoriqueAchats            └─── (n) LigneCommandeAchat
                                                      │
                                                  (n) │
                                                      └─── (1) Produit
```

---

## 📝 Git Workflow

### Branch Strategy

```
main
├── Production-ready code
│
develop
├── Integration branch
│
feature/fournisseur-management
feature/order-tracking
feature/supplier-evaluation
├── Feature branches (created from develop)
│
hotfix/critical-bug
├── Hotfix branches (created from main)
```

### Commit Message Convention

```
<type>(<scope>): <subject>

<body>

<footer>
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation
- `style`: Code style (formatting, missing semicolons)
- `refactor`: Code refactoring
- `test`: Adding or updating tests
- `chore`: Build process, dependencies

**Examples:**
```
feat(supplier): add supplier evaluation algorithm
fix(order): correct status transition validation
docs(api): update swagger documentation
test(service): add unit tests for EvaluationService
```

### Workflow Example

```bash
# 1. Create feature branch
git checkout develop
git pull origin develop
git checkout -b feature/supplier-ranking

# 2. Make changes
git add src/main/java/tn/itbs/note/service/FournisseurService.java
git commit -m "feat(supplier): implement rating calculation algorithm"

# 3. Push and create pull request
git push origin feature/supplier-ranking

# 4. After review and approval, merge to develop
git checkout develop
git merge feature/supplier-ranking

# 5. Eventually merge develop to main for release
git checkout main
git merge develop
git tag -a v1.0.0 -m "Release version 1.0.0"
```

---

## ☁️ Deployment

### Local Development

```bash
# Terminal 1: Start MySQL
docker run -d --name mysql-local \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=systeme_gestion_db \
  -p 3306:3306 \
  mysql:8.0

# Terminal 2: Run Spring Boot
./gradlew bootRun

# Terminal 3: Run Angular
cd frontend
ng serve
```

### Docker Compose (Recommended)

```bash
# Build and start all services
docker-compose up -d

# Access application
# API: http://localhost:8080/api
# Swagger: http://localhost:8080/api/swagger-ui.html
# Database: localhost:3306
```

### Cloud Deployment (Google Cloud Platform)

#### Option 1: Cloud Run

```bash
# Build container
docker build -t gcr.io/PROJECT_ID/systeme-gestion-api .

# Push to Container Registry
docker push gcr.io/PROJECT_ID/systeme-gestion-api

# Deploy to Cloud Run
gcloud run deploy systeme-gestion-api \
  --image gcr.io/PROJECT_ID/systeme-gestion-api \
  --platform managed \
  --region us-central1 \
  --allow-unauthenticated
```

#### Option 2: Cloud SQL + Compute Engine

1. Create Cloud SQL MySQL instance
2. Deploy application to Compute Engine VM
3. Configure environment variables with Cloud SQL proxy

#### Option 3: App Engine

1. Create `app.yaml`:
```yaml
runtime: java17

env: standard

handlers:
  - url: /.*
    script: auto
```

2. Deploy:
```bash
gcloud app deploy
```

### Environment Variables

```bash
# API Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/systeme_gestion_db
SPRING_DATASOURCE_USERNAME=systeme_user
SPRING_DATASOURCE_PASSWORD=systeme_password
SPRING_JPA_HIBERNATE_DDL_AUTO=validate

# Server Configuration
SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/api

# Logging
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_TN_ITBS_NOTE=DEBUG
```

---

## 📞 Support & Documentation

### Additional Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Angular Documentation](https://angular.io/docs)
- [MySQL Documentation](https://dev.mysql.com/doc/)
- [Docker Documentation](https://docs.docker.com/)
- [OpenAPI/Swagger Specification](https://swagger.io/specification/)

### Troubleshooting

#### Database Connection Issues
- Verify MySQL is running
- Check database credentials
- Ensure database name exists

#### Port Already in Use
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

#### Docker Issues
```bash
# Check logs
docker logs container-name

# Remove all containers
docker-compose down -v

# Rebuild images
docker-compose build --no-cache
```

---

## 📄 License

This project is created for educational purposes.

## 👥 Contributors

- Initial development by the team

---

**Last Updated:** 2024
**Version:** 1.0.0
