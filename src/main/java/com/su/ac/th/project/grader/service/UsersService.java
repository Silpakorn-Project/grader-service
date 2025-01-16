package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UserEntity;
import com.su.ac.th.project.grader.model.request.UserRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.service.Transform.UsersTransform;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserRequest createUser(UserRequest userRequest){
        UsersTransform usersTransform = new UsersTransform();

        UserEntity userEntity = userRepository.save(
                usersTransform.transformUserToEntity(userRequest));

        return usersTransform.transformEntityToUser(userEntity);
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
