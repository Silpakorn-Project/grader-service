package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.user.UsersRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
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

    @InjectMocks
    private UsersService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UsersService(userRepository);
    }

    @Test
    void createUser_shouldThrowException_whenUserRequestIsNull() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(null));
        assertEquals("userRequest cannot be null", exception.getMessage());
    }


    @Test
    void createUser_shouldThrowException_whenUsernameIsNull() {
        // Test for null username in userRequest
        UsersRequest usersRequest = new UsersRequest(1L, null, "password", "email@example.com");  // Add a Long value for the ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(usersRequest));
        assertEquals("username cannot be null", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenPasswordIsNull() {
        // Test for null password in userRequest
        UsersRequest usersRequest = new UsersRequest(1L, "username", null, "email@example.com");  // Add a Long value for the ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(usersRequest));
        assertEquals("password cannot be null", exception.getMessage());
    }

    @Test
    void createUser_shouldThrowException_whenEmailIsNull() {
        // Test for null email in userRequest
        UsersRequest usersRequest = new UsersRequest(1L, "username", "password", null);  // Add a Long value for the ID

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(usersRequest));
        assertEquals("email cannot be null", exception.getMessage());
    }

   @Test
   void createUser_shouldSuccess() {
       // Arrange
       UsersRequest usersRequest = new UsersRequest();
       usersRequest.setId(1L);
       usersRequest.setUsername("testUser");
       usersRequest.setPassword("testPassword");
       usersRequest.setEmail("test@example.com");

       UsersEntity usersEntity = new UsersEntity();
       usersEntity.setId(1L);
       usersEntity.setUsername("testUser");
       usersEntity.setPassword("testPassword");
       usersEntity.setEmail("test@example.com");

       UsersEntity usersEntity1 = DtoEntityMapper.mapToEntity(usersRequest, UsersEntity.class);
       when(userRepository.save(any(UsersEntity.class))).thenReturn(usersEntity);
       UsersRequest usersRequest1 = DtoEntityMapper.mapToDto(usersEntity, UsersRequest.class);

       // Act
       int result = userService.createUser(usersRequest);

       // Assert
       assertEquals(1, result);
       verify(userRepository, times(1)).save(any(UsersEntity.class));  // Ensure save is called once
   }


}
