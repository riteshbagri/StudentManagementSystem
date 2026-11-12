package com.studentmanagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

/**
 * JPA Entity representing a Student record in the database.
 * Maps to the 'students' table in MySQL.
 */
@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name must not be blank")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Course must not be blank")
    @Column(nullable = false)
    private String course;

    @NotNull(message = "Age must not be null")
    @Positive(message = "Age must be a positive number")
    @Column(nullable = false)
    private Integer age;

    // ─── Constructors ────────────────────────────────────────

    public Student() {}

    public Student(Long id, String name, String email, String course, Integer age) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.age = age;
    }

    // ─── Getters & Setters ───────────────────────────────────

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', email='" + email +
               "', course='" + course + "', age=" + age + '}';
    }
}

