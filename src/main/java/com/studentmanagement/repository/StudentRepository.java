package com.studentmanagement.repository;

import com.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Student entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    /**
     * Find all students enrolled in a specific course.
     *
     * @param course the course name to filter by
     * @return list of matching students
     */
    List<Student> findByCourseIgnoreCase(String course);
}

