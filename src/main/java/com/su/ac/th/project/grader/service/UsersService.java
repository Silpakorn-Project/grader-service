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

    public int createUser(UsersRequest usersRequest) {
        UsersEntity usersEntity = DtoEntityMapper.mapToEntity(usersRequest, UsersEntity.class);
        userRepository.save(usersEntity);

        return 1;
    }

    public int updateUser(UsersRequest usersRequest) {
        int rowUpdated = 0;

        UsersEntity usersEntity = userRepository
                .findById(usersRequest.getId())
                .orElseThrow(() -> new UserNotFoundException(usersRequest.getId()));

        if (usersEntity.getUsername() != null) {
            usersEntity.setUsername(usersRequest.getUsername());
            rowUpdated += 1;
        }

        if (usersEntity.getPassword() != null) {
            usersEntity.setPassword(usersRequest.getPassword());
            rowUpdated += 1;
        }

        if (usersEntity.getEmail() != null) {
            usersEntity.setEmail(usersRequest.getEmail());
            rowUpdated += 1;
        }

        usersEntity.setUpdatedAt(LocalDateTime.now());

        userRepository.save(usersEntity);
        return rowUpdated;
    }

    public Long deleteUser(UsersRequest usersRequest) {
        userRepository.deleteById(usersRequest.getId());
        return usersRequest.getId();
    }
}
