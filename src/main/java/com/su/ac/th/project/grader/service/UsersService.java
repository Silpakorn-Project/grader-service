package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.model.UsersModel;
import com.su.ac.th.project.grader.dto.request.UsersRequest;
import com.su.ac.th.project.grader.repository.jpa.UserRepository;
import com.su.ac.th.project.grader.service.components.TransformUsersComponents;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UserRepository userRepository;

    public UsersService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsersRequest createUser(UsersRequest usersRequest){


        TransformUsersComponents transformUsersComponents = new TransformUsersComponents();

        UsersModel usersModel = userRepository.save(
                transformUsersComponents.transformUserToEntity(usersRequest));

        return transformUsersComponents.transformEntityToUser(usersModel);
    }

}
