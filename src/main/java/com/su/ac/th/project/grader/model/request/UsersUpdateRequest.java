package com.su.ac.th.project.grader.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersUpdateRequest {

    @Schema(description = "User ID", example = "1")
    @NotNull(message = "User id is required")
    private Long id;

    @Schema(description = "Username", example = "admin")
    private String username;

    @Schema(description = "Password", example = "1234")
    private String password;

    @Schema(description = "Email", example = "admin@gmail.com")
    @Email(message = "Invalid email format")
    private String email;

}
