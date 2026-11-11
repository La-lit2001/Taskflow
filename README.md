# ⚡ TaskFlow — Kanban REST API

A personal Kanban REST API built with **Spring Boot 3**, **MySQL**, and **JWT authentication**. It is a learning project: there is no test suite and no deployment.

## 🧰 Tech Stack
`Java 17` · `Spring Boot 3` · `Spring Security` · `MySQL 8` · `JPA / Hibernate` · `JWT` · `Maven`

## 🚀 API Endpoints

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/api/auth/register` | ❌ | Register new user |
| POST | `/api/auth/login` | ❌ | Login → returns JWT token |
| GET | `/api/tasks` | ✅ | Get all your tasks |
| GET | `/api/tasks?status=TODO` | ✅ | Filter tasks by status |
| GET | `/api/tasks?priority=HIGH` | ✅ | Filter tasks by priority |
| GET | `/api/tasks/stats` | ✅ | Task count summary |
| POST | `/api/tasks` | ✅ | Create a new task |
| PUT | `/api/tasks/{id}` | ✅ | Update a task |
| DELETE | `/api/tasks/{id}` | ✅ | Delete a task |

## ⚙️ Setup & Run

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8 (or XAMPP)

### Steps

**1. Create the database**
```sql
CREATE DATABASE taskflow_db;
```

**2. Update credentials**

Edit `src/main/resources/application.yml`:
```yaml
username: root
password: your_mysql_password   # leave blank if using XAMPP
```

**3. Run**
```bash
mvn spring-boot:run
```
App starts at `http://localhost:8080`

## 🧪 Test the API

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"lalit","email":"lalit@email.com","password":"pass123"}'

# Login — copy the token from the response
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"lalit","password":"pass123"}'

# Create a task
curl -X POST http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Build portfolio","status":"IN_PROGRESS","priority":"HIGH"}'

# Get all tasks
curl http://localhost:8080/api/tasks \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## 👤 Author
**Lalit Gonji** · Sydney, Australia  
[github.com/La-lit2001](https://github.com/La-lit2001) · [linkedin.com/in/lalit-gonji](https://www.linkedin.com/in/lalit-gonji/)
