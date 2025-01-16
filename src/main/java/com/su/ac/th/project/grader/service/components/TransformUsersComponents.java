package com.su.ac.th.project.grader.service.components;

import com.su.ac.th.project.grader.model.UsersModel;
import com.su.ac.th.project.grader.dto.request.UsersRequest;
import org.springframework.stereotype.Component;

@Component
public class TransformUsersComponents {

    public UsersModel transformUserToEntity(UsersRequest usersRequest) {
        UsersModel usersModel = new UsersModel();
        usersModel.setUsername(usersRequest.getUsername());
        usersModel.setPassword(usersRequest.getPassword());
        usersModel.setEmail(usersRequest.getEmail());
        return usersModel;
    }

    public UsersRequest transformEntityToUser(UsersModel usersModel) {
        return UsersRequest.builder()
                .id(usersModel.getId())
                .username(usersModel.getUsername())
                .password(usersModel.getPassword())
                .email(usersModel.getEmail())
                .build();
    }

}
