package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UserEntity;
import com.su.ac.th.project.grader.model.request.UserRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.service.Transform.UsersTransform;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UsersServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UsersTransform usersTransform; 

    @InjectMocks
    private UsersService UsersService;
    @Autowired
    private UsersService usersService;

    @Test
    public void testCreateUser_withNullUserRequest_shouldThrowException() {
        UserRequest userRequest = null;

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            UsersService.createUser(userRequest);
        });

        assertEquals("userRequest cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateUser_withNullUsername_shouldThrowException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(null);
        userRequest.setPassword("password");
        userRequest.setEmail("email@example.com");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            UsersService.createUser(userRequest);
        });

        assertEquals("username cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateUser_withValidUserRequest_shouldCreateUser() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");
        userRequest.setEmail("email@example.com");

        UserEntity mockUserEntity = new UserEntity();
        mockUserEntity.setUsername("testuser");
        mockUserEntity.setPassword("password");
        mockUserEntity.setEmail("email@example.com");

        when(usersTransform.transformUserToEntity(userRequest)).thenReturn(mockUserEntity);
        when(userRepository.save(mockUserEntity)).thenReturn(mockUserEntity);
        when(usersTransform.transformEntityToUser(mockUserEntity)).thenReturn(userRequest);

        UserRequest result = usersService.createUser(userRequest);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("email@example.com", result.getEmail());

    }

    @Test
    public void testCreateUser_withNullPassword_shouldThrowException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword(null);
        userRequest.setEmail("email@example.com");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            UsersService.createUser(userRequest);
        });

        assertEquals("password cannot be null", exception.getMessage());
    }

    @Test
    public void testCreateUser_withNullEmail_shouldThrowException() {
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername("testuser");
        userRequest.setPassword("password");
        userRequest.setEmail(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            UsersService.createUser(userRequest);
        });

        assertEquals("email cannot be null", exception.getMessage());
    }


}
