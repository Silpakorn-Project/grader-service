package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.UsersRequest;
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

    public UsersRequest createUser(UsersRequest usersRequest){

        if (Objects.isNull(usersRequest)) {
            throw new RuntimeException("userRequest cannot be null");
        }
        if (Objects.isNull(usersRequest.getUsername())) {
            throw new RuntimeException("username cannot be null");
        }
        if (Objects.isNull(usersRequest.getPassword())) {
            throw new RuntimeException("password cannot be null");
        }
        if (Objects.isNull(usersRequest.getEmail())) {
            throw new RuntimeException("email cannot be null");
        }


        UsersTransform usersTransform = new UsersTransform();

        UsersEntity usersEntity = userRepository.save(
                usersTransform.transformUserToEntity(usersRequest));

        return usersTransform.transformEntityToUser(usersEntity);
    }

    public UsersRequest updateUser(UsersRequest usersRequest) {
        UsersTransform usersTransform = new UsersTransform();
        return usersTransform.transformEntityToUser(
                userRepository.findById(usersRequest.getId()).map(existingUsers -> {
                existingUsers.setUsername(usersRequest.getUsername());
                existingUsers.setPassword(usersRequest.getPassword());
                existingUsers.setEmail(usersRequest.getEmail());
                existingUsers.setUpdatedAt(LocalDateTime.now());
                return userRepository.save(existingUsers);
            }).orElseThrow( () -> new RuntimeException("User not found")));
    }

    public Long deleteUser(UsersRequest usersRequest) {
        userRepository.deleteById(usersRequest.getId());
        return usersRequest.getId();
    }
}
