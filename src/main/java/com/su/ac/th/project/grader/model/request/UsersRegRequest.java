package com.su.ac.th.project.grader.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsersRegRequest {

    @Schema(description = "Username", example = "admin")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "1234")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Schema(description = "Email", example = "admin@gmail.com")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

}
