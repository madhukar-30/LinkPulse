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

## 🏗️ Architecture

LinkPulse follows a classic **layered architecture**, keeping each concern isolated and testable:

```
Client
  │
  ▼
Controller Layer   →  REST endpoints, request/response DTOs, input validation
  │
  ▼
Service Layer      →  Business logic (link creation, analytics aggregation, auth)
  │
  ▼
Repository Layer   →  Spring Data JPA interfaces for data access
  │
  ▼
Database           →  MySQL, managed via Hibernate ORM
```

- **Controller layer** — exposes the REST API (`AuthController`, `LinkController`, `AnalyticsController`, `RedirectController`) and delegates all logic downstream.
- **Service layer** — contains the core business rules, including link generation, ownership checks, and analytics aggregation.
- **Repository layer** — Spring Data JPA repositories provide clean, declarative data access without hand-written SQL.
- **Security layer** — a custom `JwtAuthenticationFilter` sits in front of the filter chain, validating JWTs on every request before it reaches a controller. Spring Security enforces stateless, session-free authorization based on the resolved user.
- **Persistence** — Hibernate maps entities (`User`, `Link`, `ClickEvent`, `Role`) to MySQL tables and handles schema management.

This separation keeps the codebase easy to navigate, test, and extend — each layer only knows about the one directly below it.

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

## ⚙️ Environment Variables

LinkPulse is configured entirely through environment variables, with sensible local defaults defined in `application.properties`.

| Variable | Description | Default (local) |
|---|---|---|
| `DB_URL` | JDBC connection string for MySQL | `jdbc:mysql://localhost:3306/linkpulse` |
| `DB_USERNAME` | Database username | `root` |
| `DB_PASSWORD` | Database password | `password` |
| `JWT_SECRET` | Secret key used to sign and verify JWTs (32+ characters) | *required, no default* |
| `JWT_EXPIRATION` | JWT token lifetime, in milliseconds | `3600000` (1 hour) |
| `APP_BASE_URL` | Public base URL used when building short links and QR codes | *required, no default* |

> ⚠️ `JWT_SECRET` and `APP_BASE_URL` have no defaults in Docker Compose and **must** be set before starting the stack — see the [Docker](#docker) section below.

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

## 🔒 Security

LinkPulse is built with security as a first-class concern, not an afterthought:

- **JWT Authentication** — every protected endpoint requires a valid, signed JWT, verified on each request by a custom `JwtAuthenticationFilter`.
- **Spring Security** — enforces a stateless filter chain (no server-side sessions), with Swagger and public redirect routes explicitly whitelisted and everything else locked down by default.
- **BCrypt Password Hashing** — user passwords are never stored in plain text; `BCryptPasswordEncoder` hashes them before persistence.
- **Ownership Validation** — link mutations (update, delete, view analytics) are scoped to the authenticated user, preventing one user from accessing or modifying another user's links.

---

## Docker

The project ships with a multi-stage `Dockerfile` and a `docker-compose.yml` that provisions both the backend and a MySQL instance.

Set the required runtime values before starting the stack:

```powershell
$env:JWT_SECRET = "replace-with-a-secure-secret-of-at-least-32-characters"
$env:APP_BASE_URL = "https://links.example.com"
```

### Build manually

Use this if you just want to build the backend image on its own — for example, to inspect it, push it to a registry, or run it against a database you're managing separately.

```bash
docker build -t linkpulse-backend .
```

### Run with Docker Compose

Use this for local development or a quick full-stack spin-up — it builds the backend image, starts a MySQL container with a healthcheck, and wires them together automatically.

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
