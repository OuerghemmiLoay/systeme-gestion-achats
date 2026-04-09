# Quick Start Guide - Système de Gestion des Achats et Fournisseurs

## 🚀 5-Minute Quick Start

### Prerequisites
- Docker Desktop installed and running
- Git
- Browser

### Option 1: Run with Docker (Easiest)

```bash
# 1. Navigate to project directory
cd D:\eclipse-workspace\Systeme_de_gestion

# 2. Start all services with Docker Compose
docker-compose up -d

# 3. Wait for services to be healthy (about 30 seconds)
docker-compose ps

# 4. Access the application
# API Swagger UI:  http://localhost:8080/api/swagger-ui.html
# API Base URL:    http://localhost:8080/api
# MySQL:           localhost:3306 (user: systeme_user, pass: systeme_password)
```

**That's it! The API is now running.**

---

### Option 2: Local Development (Requires Java 17 + MySQL)

#### Step 1: Database Setup

```bash
# Option A: Using Docker for just MySQL
docker run -d --name mysql-local \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=systeme_gestion_db \
  -p 3306:3306 \
  mysql:8.0

# Option B: Or use existing MySQL 8.0 installation
# Just create the database:
# CREATE DATABASE systeme_gestion_db;
```

#### Step 2: Run Spring Boot API

```bash
# In project root directory
cd D:\eclipse-workspace\Systeme_de_gestion

# Build
./gradlew clean build

# Run
./gradlew bootRun

# Or run the JAR directly
java -jar build/libs/Systeme_de_gestion-0.0.1-SNAPSHOT.jar
```

**API will be available at: `http://localhost:8080/api`**

#### Step 3: Access Swagger UI

Open in browser: `http://localhost:8080/api/swagger-ui.html`

---

## 📚 Try the API

### 1. Create a Supplier

```bash
curl -X POST http://localhost:8080/api/fournisseurs \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Supplier ABC",
    "contact": "contact@supplierabc.com",
    "qualite_service": 4
  }'
```

### 2. Create a Product

```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Product X",
    "description": "Description of Product X",
    "prix_reference": 100.00
  }'
```

### 3. Create a Purchase Order

```bash
curl -X POST http://localhost:8080/api/commandes \
  -H "Content-Type: application/json" \
  -d '{
    "fournisseurId": 1,
    "montant": 500.00,
    "notes": "Initial order"
  }'
```

### 4. Add Line Item to Order

```bash
curl -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d '{
    "commandeId": 1,
    "produitId": 1,
    "quantite": 5,
    "prix_unitaire": 100.00
  }'
```

### 5. Update Order Status

```bash
# Confirm order
curl -X PATCH http://localhost:8080/api/commandes/1/status/CONFIRMED

# Ship order
curl -X PATCH http://localhost:8080/api/commandes/1/status/SHIPPED

# Deliver order (updates supplier rating)
curl -X PATCH http://localhost:8080/api/commandes/1/status/DELIVERED
```

### 6. Evaluate Supplier

```bash
curl -X GET http://localhost:8080/api/evaluations/supplier/1
```

### 7. Compare Offers for Product

```bash
curl -X GET http://localhost:8080/api/evaluations/compare-offers/Product%20X
```

---

## 🔗 Available Endpoints

### Suppliers
- `GET /fournisseurs` - List all
- `GET /fournisseurs/{id}` - Get one
- `GET /fournisseurs/ranked` - Ranked by rating
- `GET /fournisseurs/high-quality` - Quality >= 4
- `POST /fournisseurs` - Create
- `PUT /fournisseurs/{id}` - Update
- `DELETE /fournisseurs/{id}` - Delete

### Products
- `GET /produits` - List all
- `GET /produits/{id}` - Get one
- `POST /produits` - Create
- `PUT /produits/{id}` - Update
- `DELETE /produits/{id}` - Delete

### Purchase Orders
- `GET /commandes` - List all
- `GET /commandes/{id}` - Get one
- `GET /commandes/fournisseur/{id}` - By supplier
- `GET /commandes/statut/{status}` - By status
- `POST /commandes` - Create
- `PUT /commandes/{id}` - Update
- `PATCH /commandes/{id}/status/{status}` - Update status
- `DELETE /commandes/{id}` - Delete

### Order Line Items
- `GET /lignes-commande/commande/{id}` - Get all for order
- `GET /lignes-commande/{id}` - Get one
- `POST /lignes-commande` - Create
- `PUT /lignes-commande/{id}` - Update
- `DELETE /lignes-commande/{id}` - Delete

### Evaluations
- `GET /evaluations/supplier/{id}` - Evaluate supplier
- `GET /evaluations/all` - Evaluate all suppliers
- `GET /evaluations/compare-offers/{product}` - Compare offers

---

## 📊 Sample Data

### Supplier Statuses
- **PENDING** - Initial status
- **CONFIRMED** - Order confirmed with supplier
- **SHIPPED** - Order has been shipped
- **DELIVERED** - Order delivered (updates supplier rating)
- **CANCELLED** - Order cancelled

### Quality Service Ratings
- 1 - Very Poor
- 2 - Poor
- 3 - Average
- 4 - Good
- 5 - Excellent

---

## 🛑 Stop Services

```bash
# Docker Compose
docker-compose down

# To also remove database volume
docker-compose down -v

# Kill individual containers
docker stop container-name
docker rm container-name
```

---

## 🐛 Troubleshooting

### Port Already in Use

```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Linux/Mac
lsof -i :8080
kill -9 <PID>
```

### Database Connection Errors

```bash
# Check MySQL is running
docker ps

# View Docker logs
docker logs systeme-gestion-mysql

# Verify credentials in application.properties
cat src/main/resources/application.properties
```

### Build Fails

```bash
# Clean and rebuild
./gradlew clean build --refresh-dependencies

# Or skip tests
./gradlew clean build -x test
```

---

## 📖 Full Documentation

See detailed documentation in:
- `README.md` - Comprehensive guide with architecture and deployment
- `ANGULAR_SETUP.md` - Angular front-end setup instructions
- `IMPLEMENTATION_SUMMARY.md` - Technical implementation details

---

## ✅ Next Steps

1. ✅ **API is running** - Try the endpoints above
2. 📱 **Set up Angular** - Follow `ANGULAR_SETUP.md`
3. 🧪 **Write tests** - Add unit and integration tests
4. 📦 **Deploy** - Follow deployment section in `README.md`

---

**Happy coding! 🚀**

For full documentation, see `README.md`
