# Angular Project Setup Guide

## Prerequisites
- Node.js 18+ and npm
- Angular CLI 18+

## Installation

```bash
# Navigate to workspace root
cd D:\eclipse-workspace

# Create Angular project
ng new frontend --routing --style=scss --skip-git=true

# Navigate to frontend directory
cd frontend

# Install dependencies (if not done automatically)
npm install

# Install Angular Material (optional, for better UI)
ng add @angular/material

# Install HTTP client (should be included, but verify)
npm install --save @angular/common/http
```

## Project Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── core/                    # Core module (services, interceptors)
│   │   │   ├── services/
│   │   │   │   ├── api.service.ts
│   │   │   │   ├── fournisseur.service.ts
│   │   │   │   ├── commande.service.ts
│   │   │   │   ├── evaluation.service.ts
│   │   │   │   └── produit.service.ts
│   │   │   ├── interceptors/
│   │   │   │   └── error.interceptor.ts
│   │   │   └── core.module.ts
│   │   ├── shared/                  # Shared components and modules
│   │   │   ├── components/
│   │   │   │   ├── header/
│   │   │   │   ├── footer/
│   │   │   │   └── sidebar/
│   │   │   └── shared.module.ts
│   │   ├── features/                # Feature modules (lazy-loaded)
│   │   │   ├── fournisseurs/
│   │   │   │   ├── pages/
│   │   │   │   │   ├── list/
│   │   │   │   │   ├── detail/
│   │   │   │   │   └── form/
│   │   │   │   ├── fournisseurs-routing.module.ts
│   │   │   │   └── fournisseurs.module.ts
│   │   │   ├── commandes/
│   │   │   ├── evaluations/
│   │   │   ├── produits/
│   │   │   └── offres/
│   │   ├── app.component.ts
│   │   ├── app.routing.module.ts
│   │   └── app.module.ts
│   ├── assets/
│   ├── styles/
│   ├── main.ts
│   └── index.html
├── angular.json
├── package.json
└── tsconfig.json
```

## Key Services to Create

### 1. API Service (Base)
- Handles all HTTP requests
- Centralized API base URL
- Error handling

### 2. Fournisseur Service
- Get all suppliers
- Create, update, delete supplier
- Search suppliers
- Get high-quality suppliers
- Get supplier rating

### 3. Commande Service
- Get all orders
- Filter by supplier, status, date range
- Create, update, delete order
- Update order status (delivery tracking)

### 4. Evaluation Service
- Evaluate specific supplier
- Evaluate all suppliers
- Compare offers for a product

### 5. Produit Service
- CRUD operations for products
- Search products

## Features to Implement

### 1. Dashboard
- Overview of all suppliers and their ratings
- Recent orders
- Top-performing suppliers
- Quick statistics (total orders, average delivery time)

### 2. Suppliers Management
- List all suppliers with filtering
- Create/Edit supplier form
- Detail view with evaluation metrics
- Delete supplier (with confirmation)

### 3. Purchase Orders
- List orders with advanced filters
- Create new order
- Add/Remove line items
- Update order status with timeline
- Delivery tracking

### 4. Product Management
- CRUD for products
- Search functionality

### 5. Evaluations & Reports
- Supplier rating dashboard
- Delivery performance chart
- Cost comparison
- Quality metrics

### 6. Offers Comparison
- Compare suppliers for same product
- Best offer recommendation
- Price/Quality matrix

## Running the Angular Application

```bash
# Development server
ng serve --open

# Build for production
ng build --configuration production

# Run tests
ng test

# Run end-to-end tests
ng e2e
```

## Environment Configuration

Create `src/environments/environment.ts`:

```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

Create `src/environments/environment.prod.ts`:

```typescript
export const environment = {
  production: true,
  apiUrl: 'https://api.example.com'
};
```

## Integration with Spring Boot API

- API Base URL: `http://localhost:8080/api`
- Swagger UI: `http://localhost:8080/api/swagger-ui.html`
- API Docs: `http://localhost:8080/api/v3/api-docs`

## Styling

Using SCSS for styling with Angular Material components:

```bash
npm install @angular/material @angular/cdk
ng add @angular/material
```

## State Management (Optional)

For complex state, consider:
- NgRx for reactive state management
- RxJS Subjects for simple shared state

```bash
npm install @ngrx/store @ngrx/effects @ngrx/entity
```

## Testing

```bash
# Unit tests with Karma + Jasmine
ng test

# E2E tests with Cypress (recommended over Protractor)
npm install --save-dev cypress
npx cypress open
```

## Deployment

### Local Development
```bash
ng serve --open
```

### Docker Build
```bash
# Build Docker image with Nginx
docker build -f Dockerfile.frontend -t systeme-gestion-frontend .

# Run container
docker run -p 80:80 systeme-gestion-frontend
```

### Production Build
```bash
ng build --configuration production
# Serve with Nginx or deploy to cloud platform
```
