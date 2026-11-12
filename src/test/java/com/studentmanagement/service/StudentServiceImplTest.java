package com.studentmanagement.service;

import com.studentmanagement.entity.Student;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StudentServiceImpl using Mockito.
 * No database or Spring context required — pure unit tests.
 */
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student sampleStudent;

    @BeforeEach
    void setUp() {
        sampleStudent = new Student(1L, "Rahul Sharma", "rahul@example.com", "B.Tech CSE", 21);
    }

    // ─── createStudent ───────────────────────────────────────

    @Test
    void createStudent_shouldReturnSavedStudent() {
        when(studentRepository.save(sampleStudent)).thenReturn(sampleStudent);

        Student result = studentService.createStudent(sampleStudent);

        assertThat(result).isEqualTo(sampleStudent);
        verify(studentRepository, times(1)).save(sampleStudent);
    }

    // ─── getAllStudents ──────────────────────────────────────

    @Test
    void getAllStudents_shouldReturnListOfStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(sampleStudent));

        List<Student> result = studentService.getAllStudents();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Rahul Sharma");
    }

    // ─── getStudentById ──────────────────────────────────────

    @Test
    void getStudentById_whenFound_shouldReturnStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        Student result = studentService.getStudentById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo("rahul@example.com");
    }

    @Test
    void getStudentById_whenNotFound_shouldThrowStudentNotFoundException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.getStudentById(99L))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("99");
    }

    // ─── updateStudent ───────────────────────────────────────

    @Test
    void updateStudent_whenFound_shouldReturnUpdatedStudent() {
        Student updated = new Student(null, "Priya Verma", "priya@example.com", "BCA", 20);
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepository.save(any(Student.class))).thenAnswer(inv -> inv.getArgument(0));

        Student result = studentService.updateStudent(1L, updated);

        assertThat(result.getName()).isEqualTo("Priya Verma");
        assertThat(result.getCourse()).isEqualTo("BCA");
    }

    @Test
    void updateStudent_whenNotFound_shouldThrowStudentNotFoundException() {
        when(studentRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studentService.updateStudent(99L, sampleStudent))
                .isInstanceOf(StudentNotFoundException.class);
    }

    // ─── deleteStudent ───────────────────────────────────────

    @Test
    void deleteStudent_whenFound_shouldDelete() {
        when(studentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentRepository).deleteById(1L);

        assertThatCode(() -> studentService.deleteStudent(1L)).doesNotThrowAnyException();
        verify(studentRepository).deleteById(1L);
    }

    @Test
    void deleteStudent_whenNotFound_shouldThrowStudentNotFoundException() {
        when(studentRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> studentService.deleteStudent(99L))
                .isInstanceOf(StudentNotFoundException.class);
    }

    // ─── getStudentsByCourse ─────────────────────────────────

    @Test
    void getStudentsByCourse_shouldReturnMatchingStudents() {
        when(studentRepository.findByCourseIgnoreCase("B.Tech CSE"))
                .thenReturn(List.of(sampleStudent));

        List<Student> result = studentService.getStudentsByCourse("B.Tech CSE");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourse()).isEqualTo("B.Tech CSE");
    }
}

