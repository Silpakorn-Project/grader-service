package com.su.ac.th.project.grader.service.Transform;

import com.su.ac.th.project.grader.entity.UsersEntity;
import com.su.ac.th.project.grader.Model.request.UsersRequest;
import org.springframework.stereotype.Component;

@Component
public class UsersTransform {

    public UsersEntity transformUserToEntity(UsersRequest usersRequest) {
        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setUsername(usersRequest.getUsername());
        usersEntity.setPassword(usersRequest.getPassword());
        usersEntity.setEmail(usersRequest.getEmail());
        return usersEntity;
    }

    public UsersRequest transformEntityToUser(UsersEntity usersEntity) {
        return UsersRequest.builder()
                .id(usersEntity.getId())
                .username(usersEntity.getUsername())
                .password(usersEntity.getPassword())
                .email(usersEntity.getEmail())
                .build();
    }

}
