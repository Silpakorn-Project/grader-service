package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.model.UsersModel;
import com.su.ac.th.project.grader.dto.request.UsersRequest;
import com.su.ac.th.project.grader.repository.jpa.UsersRepository;
import com.su.ac.th.project.grader.service.Transform.UsersTransform;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<UsersModel> getAllUsers() {
        return usersRepository.findAll();
    }

    public UsersRequest createUser(UsersRequest usersRequest){
        UsersTransform usersTransform = new UsersTransform();

        UsersModel usersModel = usersRepository.save(
                usersTransform.transformUserToEntity(usersRequest));

        return usersTransform.transformEntityToUser(usersModel);
    }

    public UsersRequest updateUser(UsersRequest usersRequest) {
        UsersTransform usersTransform = new UsersTransform();
        return usersTransform.transformEntityToUser(
                usersRepository.findById(usersRequest.getId()).map(existingUsers -> {
                existingUsers.setUsername(usersRequest.getUsername());
                existingUsers.setPassword(usersRequest.getPassword());
                existingUsers.setEmail(usersRequest.getEmail());
                existingUsers.setUpdatedAt(LocalDateTime.now());
                return usersRepository.save(existingUsers);
            }).orElseThrow( () -> new RuntimeException("User not found")));
    }

    public void deleteUser(UsersRequest usersRequest) {
        usersRepository.deleteById(usersRequest.getId());
    }
}
