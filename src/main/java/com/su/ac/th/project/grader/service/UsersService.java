package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.exception.user.UserNotFoundException;
import com.su.ac.th.project.grader.model.PaginationRequest;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.request.user.UsersRequest;
import com.su.ac.th.project.grader.model.request.user.UsersUpdateRequest;
import com.su.ac.th.project.grader.model.response.UserResponse;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PaginationResponse<UserResponse> getAllUsers(PaginationRequest paginationRequest) {
        Sort sort = paginationRequest.getSortBy() != null && paginationRequest.getSortType() != null
                ? Sort.by(paginationRequest.getSortType(), paginationRequest.getSortBy())
                : Sort.unsorted();

        if (PaginationUtil.isPaginationValid(paginationRequest.getOffset(), paginationRequest.getLimit())) {
            Pageable pageable = PaginationUtil.createPageable(
                    paginationRequest.getOffset(),
                    paginationRequest.getLimit(),
                    sort);

            Page<UsersEntity> userPage = userRepository.findAll(pageable);
            return PaginationUtil.createPaginationResponse(userPage, UserResponse.class);
        }

        List<UsersEntity> usersEntityList = userRepository.findAll();
        return PaginationUtil.createPaginationResponse(usersEntityList, UserResponse.class);
    }

    public UserResponse getUserById(Long id) {
        UsersEntity usersEntity = userRepository
                .findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return DtoEntityMapper.mapToDto(usersEntity, UserResponse.class);
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

    public Object deleteUser(Long id) {
        userRepository.deleteById(id);
        return null;
    }

    public void incrementUserScore(Long id, int points) {
        UsersEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setScore(user.getScore() + points);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
}
