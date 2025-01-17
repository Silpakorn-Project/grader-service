package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UserEntity;
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

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setPassword("testPassword");
        userEntity.setEmail("test@example.com");

        // Mock the behavior of UsersTransform and UserRepository
        when(usersTransform.transformUserToEntity(userRequest)).thenReturn(userEntity);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(usersTransform.transformEntityToUser(userEntity)).thenReturn(userRequest);

        // Act
        UserRequest result = userService.createUser(userRequest);

        // Assert
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("testPassword", result.getPassword());
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));  // Ensure save is called once
    }

}
