package com.su.ac.th.project.grader.model.request;

import com.su.ac.th.project.grader.util.validator.optionalnotblank.OptionalNotBlank;
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

    @Schema(description = "Username", example = "admin")
    @OptionalNotBlank
    private String username;

    @Schema(description = "Password", example = "1234")
    @OptionalNotBlank
    private String password;

    @Schema(description = "Email", example = "admin@gmail.com")
    @OptionalNotBlank
    @Email
    private String email;

}
