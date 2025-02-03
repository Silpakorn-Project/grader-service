package com.su.ac.th.project.grader.model.request.submission;

import com.su.ac.th.project.grader.util.validator.enumerated.IsEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.su.ac.th.project.grader.constant.CommonConstant.Language;

@Data
public class SubmitRequest {

    @Schema(description = "User ID", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "Problem ID", example = "1")
    @NotNull
    private Long problemId;

    @Schema(description = "Code submitted by the user", example = "print('Hello World')")
    @NotBlank
    private String code;

    @Schema(description = "Programming language used", example = "PYTHON")
    @NotNull
    @IsEnum(enumClass = Language.class)
    private String language;
}
