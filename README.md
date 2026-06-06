# Store Management API

A simple backend API for managing products and users in a store system. Built with Spring Boot, Spring Security, and H2 in-memory database.

---

## 🚀 Tech Stack

* Java 21
* Spring Boot
* Spring Web
* Spring Security
* Spring Data JPA
* H2 Database
* Maven

---

## 📁 Project Structure

```
src/main/java/com/alexandru/store_management_api

├── controller
├── service
├── repository
├── entity
├── security
└── StoreManagementApiApplication.java
```

---

## ▶️ How to Run

### 1. Start the application

```bash
./mvnw spring-boot:run
```

### 2. App will run on:

```
http://localhost:8080
```

---

## 🔐 Security

Spring Security is enabled.

Later improvements will include:

* JWT authentication
* Role-based access control (USER / ADMIN)

---

## 📌 API Endpoints

### Health Check

```
GET /health
```

Response:

```
OK
```

---

### Users

```
POST   /users        Create user
GET    /users        Get all users
GET    /users/{id}   Get user by ID
```

Example request:

```json
{
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

## 🧪 Testing API

You can test the API using:

* Postman
* VS Code REST Client

Example:

```http
POST http://localhost:8080/users
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

## 📈 Future Improvements

* Add Product management module (CRUD)
* Add JWT authentication
* Add role-based authorization
* Add DTO layer for all responses
* Add global exception handling
* Add unit and integration tests
* Add logging and request tracing

---

## 👨‍💻 Author

Alexandru Mihalache
