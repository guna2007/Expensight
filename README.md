# Expensight

A full-stack expense management application built using **Java, Spring Boot, Spring Data JPA, MySQL, Thymeleaf, Bootstrap, and REST APIs**. The application helps users manage daily expenses by organizing them into categories and tracking spending efficiently.

---

## Features

- User, Category, and Expense Management (full CRUD)
- Layered Architecture with DTOs and Service Layer
- Global Exception Handling
- MySQL Database Integration with JPA/Hibernate
- Schema and Seed Data via `schema.sql` / `data.sql`
- **Dashboard Summary** — total users, categories, expenses, and total amount
- **Pagination Support** — all listing endpoints return paginated responses with `?page=&size=`
- **Input Validation** — Jakarta Bean Validation (`@NotBlank`, `@Email`, `@Positive`, etc.)
- **Password Hashing** — BCrypt via `spring-security-crypto`; password field is write-only in JSON
- **BigDecimal** — monetary values stored as `DECIMAL(19,4)` for precision
- **Environment Variable Configuration** — `PORT`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`
- **Docker Multi-Stage Build** — Maven build stage → JRE runtime image
- **Railway-ready** — `railway.json` with health check and restart policy
- REST APIs with Swagger/OpenAPI Documentation
- Thymeleaf Frontend Pages with Bootstrap 5 Responsive UI
- Shared Navigation Bar using Thymeleaf Fragments

---

## Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- MySQL
- Lombok

### Frontend
- Thymeleaf
- HTML5
- CSS3
- Bootstrap 5
- JavaScript

### Tools
- Postman
- Maven
- Git
- GitHub
- Swagger OpenAPI
- Docker
- Railway (Cloud Deployment)

---

## Project Structure

```text
expensight
│
├── controller
├── service
│   └── impl
├── repository
├── model
├── dto
├── exception
├── config
├── util
│
├── resources
│   ├── templates
│   │   ├── dashboard.html
│   │   ├── users.html
│   │   ├── categories.html
│   │   ├── expenses.html
│   │   ├── add-expense.html
│   │   └── fragments
│   │       └── navbar.html
│   │
│   ├── static
│   │   ├── css
│   │   └── js
│   │
│   ├── application.properties
│   ├── application-dev.properties
│   ├── schema.sql
│   └── data.sql
```

---

## Database Schema

### User
- id
- name
- email
- password

### Category
- id
- name

### Expense
- id
- description
- amount
- date
- user_id
- category_id

---

## Entity Relationships

- One User -> Many Expenses
- One Category -> Many Expenses
- Many Expenses -> One User
- Many Expenses -> One Category

---

## REST API Endpoints

All listing endpoints support pagination via `?page=0&size=20` (default: page 0, size 20).

### Dashboard

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/dashboard/summary | Total users, categories, expenses, and total amount |

### Users

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/users | List all users (paginated) |
| GET | /api/users/{id} | Get user by ID |
| POST | /api/users | Create user |
| PUT | /api/users/{id} | Update user (password is optional) |
| DELETE | /api/users/{id} | Delete user (must have no expenses) |

### Categories

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/categories | List all categories (paginated) |
| GET | /api/categories/{id} | Get category by ID |
| POST | /api/categories | Create category |
| PUT | /api/categories/{id} | Update category |
| DELETE | /api/categories/{id} | Delete category (must have no expenses) |

### Expenses

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/expenses | List all expenses (paginated) |
| GET | /api/expenses/{id} | Get expense by ID |
| POST | /api/expenses | Create expense |
| PUT | /api/expenses/{id} | Update expense |
| DELETE | /api/expenses/{id} | Delete expense |
| GET | /api/expenses/user/{userId} | List expenses by user (paginated) |
| GET | /api/expenses/category/{categoryId} | List expenses by category (paginated) |

---

## Frontend Pages

| Page | URL |
|-------|-----|
| Dashboard | http://localhost:8085/ |
| Users | http://localhost:8085/users-page |
| Categories | http://localhost:8085/categories-page |
| Expenses | http://localhost:8085/expenses-page |
| Add Expense | http://localhost:8085/add-expense-page |

---

## Running the Project

### Clone Repository

```bash
git clone https://github.com/your-username/expensight.git
```

### Navigate

```bash
cd expensight
```

### Configure Database

Update environment variables or `application.properties`:

```properties
DB_URL=jdbc:mysql://localhost:3306/expensight
DB_USERNAME=root
DB_PASSWORD=your_password
```

### Run

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8085
```

## Deployment on Railway

The project includes a `Dockerfile` and `railway.json` for deployment on Railway.

### Required Environment Variables

| Variable | Description |
|----------|-------------|
| `PORT` | Server port (Railway sets this automatically) |
| `DB_URL` | MySQL connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |

### Deploy to Railway

1. Connect your GitHub repository to Railway.
2. Add a MySQL plugin via the Railway dashboard.
3. Railway automatically sets `PORT` and provides `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` to match the MySQL plugin.
4. The project will build using the `Dockerfile` and start automatically.

> **Note:** Use `--spring.profiles.active=dev` for local development with SQL logging enabled.

## Future Enhancements

- Spring Security + JWT Authentication
- Monthly Expense Analytics
- Filter Expenses by Category and Date
- Dashboard Charts
- Export to Excel/PDF

---

## Learning Outcomes

- Spring Boot Layered Architecture
- REST API Development
- JPA Entity Relationships
- Exception Handling
- Database Design
- DTO Pattern
- Swagger Documentation
- Thymeleaf Integration
- CRUD Operations
- Full Stack Application Development

---

## Author
**Lankalapalli Guna**

