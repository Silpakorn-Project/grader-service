package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.model.UsersModel;
import com.su.ac.th.project.grader.dto.request.UsersRequest;
import com.su.ac.th.project.grader.repository.jpa.UsersRepository;
import com.su.ac.th.project.grader.service.components.TransformUsersComponents;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UsersRequest createUser(UsersRequest usersRequest){
        TransformUsersComponents transformUsersComponents = new TransformUsersComponents();

        UsersModel usersModel = usersRepository.save(
                transformUsersComponents.transformUserToEntity(usersRequest));

        return transformUsersComponents.transformEntityToUser(usersModel);
    }

}
