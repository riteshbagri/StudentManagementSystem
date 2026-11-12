package com.studentmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentmanagement.entity.Student;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for StudentController using MockMvc.
 * Tests HTTP layer: request mapping, status codes, and JSON responses.
 */
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String BASE_URL = "/api/students";

    // ─── POST /api/students ──────────────────────────────────

    @Test
    void createStudent_withValidData_shouldReturn201() throws Exception {
        Student input = new Student(null, "Rahul Sharma", "rahul@example.com", "B.Tech CSE", 21);
        Student saved = new Student(1L, "Rahul Sharma", "rahul@example.com", "B.Tech CSE", 21);

        when(studentService.createStudent(any(Student.class))).thenReturn(saved);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Rahul Sharma"));
    }

    @Test
    void createStudent_withBlankName_shouldReturn400() throws Exception {
        Student invalid = new Student(null, "", "rahul@example.com", "B.Tech CSE", 21);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.name").exists());
    }

    @Test
    void createStudent_withInvalidEmail_shouldReturn400() throws Exception {
        Student invalid = new Student(null, "Rahul", "not-an-email", "B.Tech CSE", 21);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fieldErrors.email").exists());
    }

    // ─── GET /api/students ───────────────────────────────────

    @Test
    void getAllStudents_shouldReturn200WithList() throws Exception {
        List<Student> students = List.of(
                new Student(1L, "Rahul Sharma", "rahul@example.com", "B.Tech CSE", 21),
                new Student(2L, "Priya Verma", "priya@example.com", "BCA", 20)
        );
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    // ─── GET /api/students/{id} ──────────────────────────────

    @Test
    void getStudentById_whenFound_shouldReturn200() throws Exception {
        Student student = new Student(1L, "Rahul Sharma", "rahul@example.com", "B.Tech CSE", 21);
        when(studentService.getStudentById(1L)).thenReturn(student);

        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void getStudentById_whenNotFound_shouldReturn404() throws Exception {
        when(studentService.getStudentById(99L)).thenThrow(new StudentNotFoundException(99L));

        mockMvc.perform(get(BASE_URL + "/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Student not found with ID: 99"));
    }

    // ─── PUT /api/students/{id} ──────────────────────────────

    @Test
    void updateStudent_whenFound_shouldReturn200() throws Exception {
        Student updated = new Student(1L, "Rahul Updated", "rahul@example.com", "M.Tech", 22);
        when(studentService.updateStudent(eq(1L), any(Student.class))).thenReturn(updated);

        mockMvc.perform(put(BASE_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Rahul Updated"));
    }

    // ─── DELETE /api/students/{id} ───────────────────────────

    @Test
    void deleteStudent_whenFound_shouldReturn204() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_whenNotFound_shouldReturn404() throws Exception {
        doThrow(new StudentNotFoundException(99L)).when(studentService).deleteStudent(99L);

        mockMvc.perform(delete(BASE_URL + "/99"))
                .andExpect(status().isNotFound());
    }

    // ─── GET /api/students/course/{course} ───────────────────

    @Test
    void getStudentsByCourse_shouldReturn200WithList() throws Exception {
        List<Student> cseStudents = List.of(
                new Student(1L, "Rahul Sharma", "rahul@example.com", "B.Tech CSE", 21)
        );
        when(studentService.getStudentsByCourse("B.Tech CSE")).thenReturn(cseStudents);

        mockMvc.perform(get(BASE_URL + "/course/B.Tech CSE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].course").value("B.Tech CSE"));
    }
}

