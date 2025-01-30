package com.su.ac.th.project.grader.model.request;

import com.su.ac.th.project.grader.util.validator.enumerated.IsEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

import static com.su.ac.th.project.grader.constant.CommonConstant.Status;
import static com.su.ac.th.project.grader.constant.CommonConstant.Language;

@Data
public class SubmissionsRequest {

    @Schema(description = "Submission ID", example = "1")
    private Long submissionId;

    @Schema(description = "User ID", example = "1")
    @NotNull
    private Long userId;

    @Schema(description = "Problem ID", example = "1")
    @NotNull
    private Long problemId;

    @Schema(description = "Code submitted by the user", example = "print(''Hello World'')")
    @NotBlank
    private String code;

    @Schema(description = "Programming language used", example = "PYTHON")
    @NotNull
    @IsEnum(enumClass = Language.class)
    private String language;

    @Schema(description = "Status of the submission", example = "FAILED")
    @NotNull
    @IsEnum(enumClass = Status.class)
    private String status;

    @Schema(description = "Score percentage (0-100)", example = "85.5")
    @Min(value = 0, message = "Score percent must be at least 0")
    @Max(value = 100, message = "Score percent cannot exceed 100")
    private BigDecimal scorePercent;

}
