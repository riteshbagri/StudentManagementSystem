package com.studentmanagement.exception;

/**
 * Exception thrown when a Student with the given ID is not found in the database.
 * Triggers a 404 Not Found HTTP response via GlobalExceptionHandler.
 */
public class StudentNotFoundException extends RuntimeException {

    private final Long studentId;

    public StudentNotFoundException(Long studentId) {
        super("Student not found with ID: " + studentId);
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }
}

