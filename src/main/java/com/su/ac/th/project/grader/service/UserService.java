package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.model.request.UsersRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersRequest createUser(UsersRequest usersRequest){


        return userRepository.save(usersRequest);
    }

}
