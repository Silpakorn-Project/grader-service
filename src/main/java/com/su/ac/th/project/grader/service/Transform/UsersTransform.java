package com.su.ac.th.project.grader.service.Transform;

import com.su.ac.th.project.grader.entity.UserEntity;
import com.su.ac.th.project.grader.Model.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UsersTransform {

    public UserEntity transformUserToEntity(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userRequest.getUsername());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setEmail(userRequest.getEmail());
        return userEntity;
    }

    public UserRequest transformEntityToUser(UserEntity userEntity) {
        return UserRequest.builder()
                .id(userEntity.getId())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .email(userEntity.getEmail())
                .build();
    }

}
