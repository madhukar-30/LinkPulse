# LinkPulse Backend

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8.4-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

**A modern, production-ready URL shortening backend built with Spring Boot** 🔗

*Secure authentication • Real-time analytics • QR code generation • Fully containerized*

</div>

---

LinkPulse is a robust REST API backend for a URL shortening platform, built on **Spring Boot 4** and **Java 21**. It goes beyond simple link shortening — offering **JWT-secured authentication**, **click analytics with browser/OS breakdowns**, **QR code generation**, **custom aliases**, **link expiration**, and full **Swagger/OpenAPI documentation**, all wrapped in a Docker-ready deployment. Built with clean architecture and production best practices, it's designed to be both a solid portfolio piece and a real foundation for a link-management product.

---

## ✨ Features

- ✅ **JWT Authentication** — stateless, secure token-based auth
- ✅ **Spring Security** — hardened endpoint protection
- ✅ **User Registration & Login** — simple, validated onboarding flow
- ✅ **BCrypt Password Encryption** — industry-standard password hashing
- ✅ **URL Shortening** — generate compact, shareable short links
- ✅ **Custom Aliases** — pick your own short code instead of a random one
- ✅ **Link Expiration** — set links to automatically expire
- ✅ **QR Code Generation** — instant QR codes for every short link
- ✅ **Click Tracking** — every redirect is logged and measurable
- ✅ **Analytics** — daily click trends, browser stats, OS stats, recent clicks
- ✅ **Search** — quickly find links by keyword
- ✅ **Filtering** — narrow down results with flexible filters
- ✅ **Pagination** — efficient handling of large link collections
- ✅ **Swagger / OpenAPI Documentation** — interactive, always up-to-date API docs
- ✅ **Docker Support** — one-command containerized deployment
- ✅ **MySQL Database** — reliable relational persistence
- ✅ **RESTful APIs** — clean, predictable, resource-oriented endpoints

---

## 🛠️ Tech Stack

| Category | Technology |
|---|---|
| **Language** | Java 21 |
| **Framework** | Spring Boot |
| **Security** | Spring Security |
| **Persistence** | Spring Data JPA |
| **ORM** | Hibernate |
| **Database** | MySQL |
| **Auth** | JWT (JSON Web Tokens) |
| **Build Tool** | Maven |
| **Containerization** | Docker |
| **Orchestration** | Docker Compose |
| **API Docs** | Swagger / OpenAPI |

---

## 📁 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/linkpulse/backend/
│   │   │   ├── analytics/        # User-agent parsing & analytics helpers
│   │   │   ├── config/           # OpenAPI, app & analytics configuration
│   │   │   ├── controller/       # REST controllers (Auth, Link, Analytics, Redirect)
│   │   │   ├── dto/              # Request/response data transfer objects
│   │   │   ├── entity/           # JPA entities (User, Link, ClickEvent, Role)
│   │   │   ├── exception/        # Global exception handling
│   │   │   ├── repository/       # Spring Data JPA repositories
│   │   │   ├── security/         # JWT filter, security config, user details
│   │   │   ├── service/          # Business logic layer
│   │   │   └── BackendApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/linkpulse/backend/
├── Dockerfile
├── docker-compose.yml
├── pom.xml
└── mvnw / mvnw.cmd
```

---

## 📖 API Documentation

LinkPulse ships with interactive **Swagger UI**, generated automatically from the codebase. Once the application is running, explore and test every endpoint directly from your browser:

```
http://localhost:8080/swagger-ui/index.html
```

| Resource | Description |
|---|---|
| `/api/auth/**` | Registration & login |
| `/api/links/**` | Create, list, update, delete, and get QR codes for links |
| `/api/links/{id}/analytics` | Per-link analytics (clicks, browsers, OS, trends) |
| `/{shortCode}` | Public redirect endpoint |

---

## 🚀 Running Locally

### Prerequisites

- ☕ Java 21
- 📦 Maven
- 🐬 MySQL

### Build & Run

```bash
mvn clean install
```

```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.

---

## Docker

Set the required runtime values before starting the stack:

```powershell
$env:JWT_SECRET = "replace-with-a-secure-secret-of-at-least-32-characters"
$env:APP_BASE_URL = "https://links.example.com"
```

Build the backend image:

```bash
docker build -t linkpulse-backend .
```

Start the backend and MySQL:

```bash
docker compose up --build -d
```

Stop the containers:

```bash
docker compose down
```

Use `docker compose down -v` to also remove the persistent MySQL volume.

---

<div align="center">

Made with ☕ and Spring Boot

</div>
