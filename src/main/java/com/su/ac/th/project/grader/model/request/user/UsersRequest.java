package com.su.ac.th.project.grader.model.request.user;

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

    @Schema(description = "Username", example = "admin")
    @NotBlank
    private String username;

    @Schema(description = "Password", example = "1234")
    @NotBlank
    private String password;

    @Schema(description = "Email", example = "admin@gmail.com")
    @NotBlank
    @Email
    private String email;

}
