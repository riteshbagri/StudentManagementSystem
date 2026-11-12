package com.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Smoke test — verifies the Spring application context loads successfully.
 * Uses H2 in-memory database (configured in src/test/resources/application.properties).
 */
@SpringBootTest
@ActiveProfiles("test")
class StudentManagementApplicationTests {

    @Test
    void contextLoads() {
        // If this test passes, the entire Spring context started without errors.
    }
}

