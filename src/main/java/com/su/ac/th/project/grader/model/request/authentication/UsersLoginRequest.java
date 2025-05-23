package com.su.ac.th.project.grader.model.request.authentication;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsersLoginRequest {

    @Schema(description = "Username", example = "admin")
    @NotBlank
    private String username;

    @Schema(description = "Password", example = "admin123")
    @NotBlank
    private String password;

}
