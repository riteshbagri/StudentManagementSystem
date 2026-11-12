package com.studentmanagement.controller;

import com.studentmanagement.entity.Student;
import com.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller exposing all six Student Management API endpoints.
 *
 * Base URL: /api/students
 */
@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ─── POST /api/students ─────────────────────────────────
    // Add a new student → 201 Created

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student created = studentService.createStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ─── GET /api/students ──────────────────────────────────
    // Retrieve all students → 200 OK

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    // ─── GET /api/students/{id} ─────────────────────────────
    // Retrieve student by ID → 200 OK / 404 Not Found

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    // ─── PUT /api/students/{id} ─────────────────────────────
    // Update student details → 200 OK / 404 Not Found

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody Student student) {
        return ResponseEntity.ok(studentService.updateStudent(id, student));
    }

    // ─── DELETE /api/students/{id} ──────────────────────────
    // Delete a student → 204 No Content / 404 Not Found

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // ─── GET /api/students/course/{course} ──────────────────
    // Retrieve students by course → 200 OK

    @GetMapping("/course/{course}")
    public ResponseEntity<List<Student>> getStudentsByCourse(@PathVariable String course) {
        return ResponseEntity.ok(studentService.getStudentsByCourse(course));
    }
}

