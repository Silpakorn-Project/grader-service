package com.su.ac.th.project.grader.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersRequest {

    @Schema(description = "User ID", example = "1")
    private Long id;

    @Schema(description = "Username", example = "updated")
    @NotBlank(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", example = "updated1234")
    @NotBlank(message = "Password cannot be empty")
    private String password;

    @Schema(description = "Email", example = "updated@gmail.com")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

}
