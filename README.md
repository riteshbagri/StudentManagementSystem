# Student Management System

A **REST API** built with **Spring Boot 3** and **MySQL** for managing student records.  
College Assignment — Java Spring Boot Development.

---

## Technology Stack

| Technology | Version |
|---|---|
| Java | 21 |
| Spring Boot | 3.3.5 |
| Spring Web | Included |
| Spring Data JPA | Included |
| MySQL Connector/J | Latest |
| Jakarta Validation | Included |
| Maven | 3.x |

---

## Project Structure

```
StudentManagementSystem/
├── screenshots/                                       ← Postman API testing screenshots
│   ├── README.md                                      ← Screenshot guide & naming convention
│   ├── 01-POST-add-student.png                        ← (add manually after Postman testing)
│   ├── 02-GET-all-students.png
│   ├── 03-GET-student-by-id.png
│   ├── 04-PUT-update-student.png
│   ├── 05-DELETE-student.png
│   └── 06-GET-students-by-course.png
├── src/
│   ├── main/
│   │   ├── java/com/studentmanagement/
│   │   │   ├── StudentManagementApplication.java   ← Entry point
│   │   │   ├── controller/
│   │   │   │   └── StudentController.java          ← REST endpoints
│   │   │   ├── service/
│   │   │   │   ├── StudentService.java             ← Interface
│   │   │   │   └── StudentServiceImpl.java         ← Business logic
│   │   │   ├── repository/
│   │   │   │   └── StudentRepository.java          ← JPA repository
│   │   │   ├── entity/
│   │   │   │   └── Student.java                   ← JPA entity
│   │   │   └── exception/
│   │   │       ├── StudentNotFoundException.java
│   │   │       └── GlobalExceptionHandler.java
│   │   └── resources/
│   │       └── application.properties             ← MySQL config
│   └── test/
│       ├── java/com/studentmanagement/
│       │   ├── StudentManagementApplicationTests.java
│       │   ├── service/StudentServiceImplTest.java
│       │   └── controller/StudentControllerTest.java
│       └── resources/
│           └── application.properties             ← H2 test config
├── StudentManagementSystem.postman_collection.json
├── pom.xml
├── .gitignore
└── README.md
```

---

## Prerequisites

Before running this project, install:

1. **Java 21** — [Download](https://adoptium.net/)
2. **Maven 3.x** — [Download](https://maven.apache.org/download.cgi)  
   *(or use the Maven Wrapper included: `mvnw.cmd`)*
3. **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/installer/)
4. **Postman** — [Download](https://www.postman.com/downloads/)

---

## Step 1 — Set Up MySQL Database

Open MySQL command line or MySQL Workbench and run:

```sql
CREATE DATABASE student_management_db;
```

Verify it was created:

```sql
SHOW DATABASES;
```

---

## Step 2 — Configure Database Credentials

The app reads credentials from **environment variables** to keep passwords out of source code.

### Option A — Set environment variables (Recommended)

**Windows CMD:**
```cmd
set DB_USERNAME=root
set DB_PASSWORD=yourpassword
```

**Windows PowerShell:**
```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "yourpassword"
```

**Linux / macOS (Bash):**
```bash
export DB_USERNAME=root
export DB_PASSWORD=yourpassword
```

### Option B — Edit `application.properties` directly (Quick local test only)

Open `src/main/resources/application.properties` and replace:

```properties
spring.datasource.username=${DB_USERNAME:root}
spring.datasource.password=${DB_PASSWORD:}
```

with your actual credentials:

```properties
spring.datasource.username=root
spring.datasource.password=yourpassword
```

> ⚠️ **Never commit real passwords to GitHub. Add `.env` files to `.gitignore`.**

---

## Step 3 — Run Maven Tests (No MySQL Required)

Tests use an H2 in-memory database automatically:

```cmd
mvnw.cmd clean test
```

Expected output:
```
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## Step 4 — Run the Application

```cmd
mvnw.cmd spring-boot:run
```

You should see:
```
Started StudentManagementApplication in X.XXX seconds
Tomcat started on port(s): 8080
```

The API is now live at: `http://localhost:8080`

---

## API Endpoints Reference

| Method | URL | Description | Response |
|--------|-----|-------------|----------|
| `POST` | `/api/students` | Add new student | `201 Created` |
| `GET` | `/api/students` | Get all students | `200 OK` |
| `GET` | `/api/students/{id}` | Get student by ID | `200 OK` / `404` |
| `PUT` | `/api/students/{id}` | Update student | `200 OK` / `404` |
| `DELETE` | `/api/students/{id}` | Delete student | `204 No Content` |
| `GET` | `/api/students/course/{course}` | Get by course | `200 OK` |

### Sample Request Body (POST / PUT)

```json
{
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "course": "B.Tech CSE",
  "age": 21
}
```

### Sample Success Response (POST)

```json
{
  "id": 1,
  "name": "Rahul Sharma",
  "email": "rahul@example.com",
  "course": "B.Tech CSE",
  "age": 21
}
```

### Sample Error Response (404)

```json
{
  "timestamp": "2024-11-12T11:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with ID: 99"
}
```

### Sample Validation Error (400)

```json
{
  "timestamp": "2024-11-12T11:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fieldErrors": {
    "name": "Name must not be blank",
    "email": "Email must be a valid email address"
  }
}
```

---

## Postman Testing Guide

### Import the Collection

1. Open **Postman**
2. Click **Import** (top left)
3. Select `StudentManagementSystem.postman_collection.json`
4. The collection appears in the left sidebar

### Testing Each Endpoint (Steps for Screenshots)

#### 1. POST — Add a Student
- Select **"1. Add a New Student"**
- Confirm Body has valid JSON
- Click **Send**
- Screenshot: Show `201 Created` status + response body with `id`

#### 2. GET — All Students
- Select **"2. Get All Students"**
- Click **Send**
- Screenshot: Show `200 OK` + JSON array with the student you just added

#### 3. GET — Student by ID
- Select **"3. Get Student by ID"**
- Change `1` in URL to the actual ID from step 1
- Click **Send**
- Screenshot: Show `200 OK` + single student JSON

#### 4. PUT — Update Student
- Select **"4. Update Student by ID"**
- Update the URL ID to match your student
- Modify the body (e.g., change course to `"M.Tech CSE"`)
- Click **Send**
- Screenshot: Show `200 OK` + updated JSON

#### 5. DELETE — Delete Student
- Select **"5. Delete Student by ID"**
- Set the URL ID
- Click **Send**
- Screenshot: Show `204 No Content` (empty body is correct!)

#### 6. GET — Students by Course
- Select **"6. Get Students by Course"**
- Change URL to match a course (e.g., `B.Tech CSE`)
- Click **Send**
- Screenshot: Show `200 OK` + filtered list

#### Bonus — Test Validation
- Use **"EXTRA: Validation Test"** request (blank name)
- Screenshot: Show `400 Bad Request` with `fieldErrors`

---

## Running in VS Code

1. Install the **Extension Pack for Java** in VS Code
2. Open the `StudentManagementSystem` folder: `File → Open Folder`
3. VS Code auto-detects the Maven project
4. Set environment variables in the integrated terminal:
   ```powershell
   $env:DB_USERNAME = "root"
   $env:DB_PASSWORD = "yourpassword"
   ```
5. Open `StudentManagementApplication.java`
6. Click the **▷ Run** button above the `main` method
   — OR —
   Run in terminal: `mvnw.cmd spring-boot:run`

---

## Useful Maven Commands

```cmd
# Compile the project
mvnw.cmd compile

# Run all tests (uses H2, no MySQL needed)
mvnw.cmd clean test

# Package into a JAR
mvnw.cmd clean package

# Run the application
mvnw.cmd spring-boot:run

# Skip tests when packaging
mvnw.cmd clean package -DskipTests
```

---

## Upload to GitHub

```bash
# 1. Initialize git repository
git init

# 2. Stage all files
git add .

# 3. First commit
git commit -m "Initial commit: Student Management System Spring Boot REST API"

# 4. Create a new repo on github.com (do NOT initialize with README)
#    Copy the HTTPS URL e.g. https://github.com/yourusername/StudentManagementSystem.git

# 5. Add remote origin
git remote add origin https://github.com/yourusername/StudentManagementSystem.git

# 6. Push to GitHub
git branch -M main
git push -u origin main
```

> ✅ Your `.gitignore` already excludes `target/`, IDE files, and `.env` files.

---

## Notes on MySQL Integration

> The MySQL integration is configured and ready. However, the actual runtime database connection and live API tests **require MySQL to be running on your machine** with:
> - MySQL service started
> - Database `student_management_db` created
> - Correct credentials set via environment variables
>
> The unit tests (service layer + controller layer) run successfully without MySQL using H2 in-memory database.

---

---

## API Testing Screenshots

The `screenshots/` folder contains Postman evidence for all six REST API endpoints.
Add your own screenshots after testing with Postman (see [`screenshots/README.md`](screenshots/README.md) for the naming guide).

| # | Screenshot File | Endpoint | Expected Status |
|---|-----------------|----------|-----------------|
| 1 | [`01-POST-add-student.png`](screenshots/01-POST-add-student.png) | `POST /api/students` | `201 Created` |
| 2 | [`02-GET-all-students.png`](screenshots/02-GET-all-students.png) | `GET /api/students` | `200 OK` |
| 3 | [`03-GET-student-by-id.png`](screenshots/03-GET-student-by-id.png) | `GET /api/students/{id}` | `200 OK` |
| 4 | [`04-PUT-update-student.png`](screenshots/04-PUT-update-student.png) | `PUT /api/students/{id}` | `200 OK` |
| 5 | [`05-DELETE-student.png`](screenshots/05-DELETE-student.png) | `DELETE /api/students/{id}` | `204 No Content` |
| 6 | [`06-GET-students-by-course.png`](screenshots/06-GET-students-by-course.png) | `GET /api/students/course/{course}` | `200 OK` |

> **College Submission Tip:** Each screenshot should show the full Postman window with the
> request URL, HTTP method, request body (for POST/PUT), the HTTP status code, and the JSON response body.

---

## Postman Collection

The importable Postman collection is included at the project root:

```
StudentManagementSystem.postman_collection.json
```

**To import:** Open Postman → **Import** → select the file above.

---

*Built for college assignment — Java Spring Boot REST API Development*

