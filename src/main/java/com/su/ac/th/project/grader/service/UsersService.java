package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.UserRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.service.Transform.UsersTransform;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UsersEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserRequest createUser(UserRequest userRequest){

        if (Objects.isNull(userRequest)) {
            throw new RuntimeException("userRequest cannot be null");
        }
        if (Objects.isNull(userRequest.getUsername())) {
            throw new RuntimeException("username cannot be null");
        }
        if (Objects.isNull(userRequest.getPassword())) {
            throw new RuntimeException("password cannot be null");
        }
        if (Objects.isNull(userRequest.getEmail())) {
            throw new RuntimeException("email cannot be null");
        }


        UsersTransform usersTransform = new UsersTransform();

        UsersEntity usersEntity = userRepository.save(
                usersTransform.transformUserToEntity(userRequest));

        return usersTransform.transformEntityToUser(usersEntity);
    }

    public UserRequest updateUser(UserRequest userRequest) {
        UsersTransform usersTransform = new UsersTransform();
        return usersTransform.transformEntityToUser(
                userRepository.findById(userRequest.getId()).map(existingUsers -> {
                existingUsers.setUsername(userRequest.getUsername());
                existingUsers.setPassword(userRequest.getPassword());
                existingUsers.setEmail(userRequest.getEmail());
                existingUsers.setUpdatedAt(LocalDateTime.now());
                return userRepository.save(existingUsers);
            }).orElseThrow( () -> new RuntimeException("User not found")));
    }

    public Long deleteUser(UserRequest userRequest) {
        userRepository.deleteById(userRequest.getId());
        return userRequest.getId();
    }
}
