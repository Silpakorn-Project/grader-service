package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.exception.user.UserNotFoundException;
import com.su.ac.th.project.grader.model.request.UsersRequest;
import com.su.ac.th.project.grader.model.request.UsersUpdateRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public int updateUser(UsersUpdateRequest usersUpdateRequest, Long id) {
        int rowUpdated = 0;
        UsersEntity usersEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        if (usersUpdateRequest.getUsername() != null) {
            usersEntity.setUsername(usersUpdateRequest.getUsername());
            rowUpdated += 1;
        }

        if (usersUpdateRequest.getPassword() != null) {
            usersEntity.setPassword(usersUpdateRequest.getPassword());
            rowUpdated += 1;
        }

        if (usersUpdateRequest.getEmail() != null) {
            usersEntity.setEmail(usersUpdateRequest.getEmail());
            rowUpdated += 1;
        }

        usersEntity.setUpdatedAt(LocalDateTime.now());

        userRepository.save(usersEntity);
        return rowUpdated;
    }

    public Long deleteUser(Long id) {
        userRepository.deleteById(id);
        return id;
    }
}
