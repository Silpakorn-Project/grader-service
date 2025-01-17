package com.su.ac.th.project.grader.service.Transform;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.model.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UsersTransform {

    public UsersEntity transformUserToEntity(UserRequest userRequest) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(userRequest.getUsername());
        usersEntity.setPassword(userRequest.getPassword());
        usersEntity.setEmail(userRequest.getEmail());
        return usersEntity;
    }

    public UserRequest transformEntityToUser(UsersEntity usersEntity) {
        return UserRequest.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .password(usersEntity.getPassword())
                .email(usersEntity.getEmail())
                .build();
    }

}
