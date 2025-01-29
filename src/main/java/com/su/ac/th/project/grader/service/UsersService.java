package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.exception.user.UserNotFoundException;
import com.su.ac.th.project.grader.model.request.UsersRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
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

        UsersEntity u = DtoEntityMapper.mapToEntity(usersRequest, UsersEntity.class);
        UsersEntity u1 = userRepository.save(u);

        return DtoEntityMapper.mapToDto(u1, UsersRequest.class);
    }

    public UsersRequest updateUser(UsersRequest usersRequest) {

        UsersEntity u = userRepository.findById(usersRequest.getId()).map(existingUsers -> {
            existingUsers.setUsername(usersRequest.getUsername());
            existingUsers.setPassword(usersRequest.getPassword());
            existingUsers.setEmail(usersRequest.getEmail());
            existingUsers.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(existingUsers);
        }).orElseThrow( () -> new UserNotFoundException(usersRequest.getId()));

        return DtoEntityMapper.mapToDto(u, UsersRequest.class);
    }

    public Long deleteUser(UsersRequest usersRequest) {
        userRepository.deleteById(usersRequest.getId());
        return usersRequest.getId();
    }
}
