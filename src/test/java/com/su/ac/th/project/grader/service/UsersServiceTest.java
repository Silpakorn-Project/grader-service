package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.model.request.UserRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.service.Transform.UsersTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

public class UsersServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersTransform usersTransform;

    private UsersService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UsersService(userRepository);
    }

    @Test
    void createUser_shouldThrowException_whenUserRequestIsNull() {
        // Test for null username in userRequest
        UserRequest userRequest = null;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userRequest);
        });
        assertEquals("userRequest cannot be null", exception.getMessage());
    }


    @Test
    void createUser_shouldThrowException_whenUsernameIsNull() {
        // Test for null username in userRequest
        UserRequest userRequest = new UserRequest(1L, null, "password", "email@example.com");  // Add a Long value for the ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userRequest);
        });
        assertEquals("username cannot be null", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenPasswordIsNull() {
        // Test for null password in userRequest
        UserRequest userRequest = new UserRequest(1L, "username", null, "email@example.com");  // Add a Long value for the ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userRequest);
        });
        assertEquals("password cannot be null", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenEmailIsNull() {
        // Test for null email in userRequest
        UserRequest userRequest = new UserRequest(1L, "username", "password", null);  // Add a Long value for the ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(userRequest);
        });
        assertEquals("email cannot be null", exception.getMessage());
    }

}
