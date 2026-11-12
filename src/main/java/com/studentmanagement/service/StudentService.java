package com.studentmanagement.service;

import com.studentmanagement.entity.Student;

import java.util.List;

/**
 * Service interface defining business operations for Student management.
 */
public interface StudentService {

    /**
     * Save a new student record.
     */
    Student createStudent(Student student);

    /**
     * Retrieve all student records.
     */
    List<Student> getAllStudents();

    /**
     * Retrieve a student by their ID.
     *
     * @throws com.studentmanagement.exception.StudentNotFoundException if not found
     */
    Student getStudentById(Long id);

    /**
     * Update an existing student's details.
     *
     * @throws com.studentmanagement.exception.StudentNotFoundException if not found
     */
    Student updateStudent(Long id, Student updatedStudent);

    /**
     * Delete a student by their ID.
     *
     * @throws com.studentmanagement.exception.StudentNotFoundException if not found
     */
    void deleteStudent(Long id);

    /**
     * Retrieve all students enrolled in a specific course.
     */
    List<Student> getStudentsByCourse(String course);
}

