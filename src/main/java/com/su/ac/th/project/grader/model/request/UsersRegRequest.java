package com.su.ac.th.project.grader.model.request;

import lombok.Data;

@Data
public class UsersRegRequest {

    private String username;
    private String password;
    private String email;

}
