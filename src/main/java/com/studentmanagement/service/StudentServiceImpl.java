package com.studentmanagement.service;

import com.studentmanagement.entity.Student;
import com.studentmanagement.exception.StudentNotFoundException;
import com.studentmanagement.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link StudentService} containing the core business logic.
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // Constructor injection (preferred over @Autowired on field)
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ─── Create ──────────────────────────────────────────────

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // ─── Read ────────────────────────────────────────────────

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @Override
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourseIgnoreCase(course);
    }

    // ─── Update ──────────────────────────────────────────────

    @Override
    public Student updateStudent(Long id, Student updatedStudent) {
        // Verify student exists (throws 404 if not)
        Student existing = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        // Apply updates
        existing.setName(updatedStudent.getName());
        existing.setEmail(updatedStudent.getEmail());
        existing.setCourse(updatedStudent.getCourse());
        existing.setAge(updatedStudent.getAge());

        return studentRepository.save(existing);
    }

    // ─── Delete ──────────────────────────────────────────────

    @Override
    public void deleteStudent(Long id) {
        // Verify student exists before deleting
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }
}

