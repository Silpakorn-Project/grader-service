package com.su.ac.th.project.grader.model.request.user;

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

    @Schema(description = "Username", example = "updated")
    @OptionalNotBlank
    private String username;

    @Schema(description = "Password", example = "updated1234")
    @OptionalNotBlank
    private String password;

    @Schema(description = "Email", example = "updated@gmail.com")
    @OptionalNotBlank
    @Email
    private String email;

}
