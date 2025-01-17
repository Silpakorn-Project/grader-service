package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.UserRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.service.Transform.UsersTransform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UsersServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersTransform usersTransform;

    @InjectMocks
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

    @Test
    void createUser_shouldSuccess() {
        // Arrange
        UserRequest userRequest = new UserRequest();
        userRequest.setId(1L);
        userRequest.setUsername("testUser");
        userRequest.setPassword("testPassword");
        userRequest.setEmail("test@example.com");

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setId(1L);
        usersEntity.setUsername("testUser");
        usersEntity.setPassword("testPassword");
        usersEntity.setEmail("test@example.com");

        // Mock the behavior of UsersTransform and UserRepository
        when(usersTransform.transformUserToEntity(userRequest)).thenReturn(usersEntity);
        when(userRepository.save(any(UsersEntity.class))).thenReturn(usersEntity);
        when(usersTransform.transformEntityToUser(usersEntity)).thenReturn(userRequest);

        // Act
        UserRequest result = userService.createUser(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("testPassword", result.getPassword());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(UsersEntity.class));  // Ensure save is called once
    }

}
