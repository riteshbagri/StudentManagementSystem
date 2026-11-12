# API Testing Screenshots

This folder contains Postman screenshots demonstrating all six REST API endpoints
of the Student Management System.

## Screenshot Naming Convention

| File | Endpoint | Expected HTTP Status |
|------|----------|----------------------|
| `01-POST-add-student.png` | `POST /api/students` | `201 Created` |
| `02-GET-all-students.png` | `GET /api/students` | `200 OK` |
| `03-GET-student-by-id.png` | `GET /api/students/{id}` | `200 OK` |
| `04-PUT-update-student.png` | `PUT /api/students/{id}` | `200 OK` |
| `05-DELETE-student.png` | `DELETE /api/students/{id}` | `204 No Content` |
| `06-GET-students-by-course.png` | `GET /api/students/course/{course}` | `200 OK` |

## How to Capture Screenshots

1. Start the application:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

2. Open **Postman** and import `StudentManagementSystem.postman_collection.json`

3. For each request:
   - Set the base URL to `http://localhost:8080`
   - Click **Send**
   - **Capture a screenshot** showing:
     - The full Postman window
     - The request method and URL
     - The request body (for POST/PUT)
     - The HTTP status code (e.g. `201 Created`)
     - The JSON response body

4. Save each screenshot to this folder using the exact filenames above.

> **Note:** Add screenshots to this folder before final GitHub submission.
> Screenshots are gitignored by default only if named `*.png` patterns —
> they are intentionally tracked here for college submission evidence.

