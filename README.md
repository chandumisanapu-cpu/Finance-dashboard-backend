# Finance Data Processing & Access Control Backend

## Overview

This project is a backend system for a finance dashboard that manages financial transactions, user roles, and access control.

It demonstrates clean backend architecture, API design, business logic implementation, and role-based authorization.

---

## Links

**GitHub Repository:**
[https://github.com/chandu-NetFSD-upGrad-B8-Python/Finance-Dashboard-Backend](https://github.com/chandu-NetFSD-upGrad-B8-Python/Finance-Dashboard-Backend)

**API Documentation (Swagger UI):**
[http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)

> The API is documented using Swagger UI and can be accessed locally after running the application.

---

## Features

### User & Role Management

* Create and manage users
* Assign roles:

  * ADMIN
  * ANALYST
  * VIEWER
* Role-based access control

---

### Transaction Management

* Create transactions (ADMIN only)
* Update transactions (ADMIN only)
* Delete transactions (ADMIN only)
* View transactions (ALL roles)
* Filter by category (ADMIN, ANALYST)

---

### Dashboard Summary

Provides aggregated financial data:

```json
{
  "totalIncome": number,
  "totalExpense": number,
  "balance": number
}
```

---

### Access Control

| Role    | Permissions                |
| ------- | -------------------------- |
| ADMIN   | Full access (CRUD + users) |
| ANALYST | Read + analytics           |
| VIEWER  | Read-only                  |

---

### Validation & Error Handling

* Prevents invalid data (e.g., negative amount)
* Returns proper HTTP status codes
* Clean error responses using global exception handling

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database (in-memory)
* Maven
* Swagger (OpenAPI)

---

## How to Run

### 1. Clone Repository

```bash
git clone https://github.com/chandu-NetFSD-upGrad-B8-Python/Finance-Dashboard-Backend.git
```

### 2. Open in IDE

* Eclipse / IntelliJ

### 3. Run Application

Run:

```
FinanceBackendApplication.java
```

### 4. Server Starts At

```
http://localhost:8081
```

---

## API Testing

You can test APIs using:

* Swagger UI (Recommended)
* Postman

---

### Required Header

All APIs require:

```
Key: role
Value: ADMIN / ANALYST / VIEWER
```

---

## API Endpoints

### Create Transaction (ADMIN)

POST `/transactions`

```json
{
  "amount": 1000,
  "type": "income",
  "category": "salary",
  "date": "2026-04-06",
  "notes": "Monthly salary"
}
```

---

### Get All Transactions

GET `/transactions`

---

### Update Transaction (ADMIN)

PUT `/transactions/{id}`

---

### Delete Transaction (ADMIN)

DELETE `/transactions/{id}`

---

### Filter by Category

GET `/transactions/category/{category}`

---

### Dashboard Summary

GET `/transactions/summary`

---

## Project Structure

```
controller   → API layer  
service      → business logic  
repository   → database access  
model        → entities  
dto          → response objects  
exception    → global error handling  
```

---

## Assumptions

* Role is passed via request header (mock authentication)
* No login/auth system implemented
* H2 database is used for simplicity (in-memory)

---

## Future Improvements

* JWT Authentication
* Pagination
* Filtering by date/type
* Deployment (Render / Railway)
* Unit & Integration Testing
* Docker support

---

## Author

**Chandu Misanapu**
