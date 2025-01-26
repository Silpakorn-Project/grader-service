package com.su.ac.th.project.grader.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UsersRegRequest {

    @Schema(description = "Username", example = "admin")
    private String username;
    @Schema(description = "Password", example = "1234")
    private String password;
    @Schema(description = "Email", example = "admin@gmail.com")
    private String email;

}
