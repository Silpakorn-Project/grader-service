package com.su.ac.th.project.grader.model.request;

import com.su.ac.th.project.grader.util.validator.enumerated.IsEnum;
import com.su.ac.th.project.grader.util.validator.optionalnotblank.OptionalNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

import static com.su.ac.th.project.grader.constant.CommonConstant.Language;
import static com.su.ac.th.project.grader.constant.CommonConstant.Status;

@Data
public class SubmissionsUpdateRequest {

    @Schema(description = "Submission ID", example = "1")
    @NotNull
    private Long submissionId;

    @Schema(description = "User ID", example = "1")
    private Long userId;

    @Schema(description = "Problem ID", example = "1")
    private Long problemId;

    @Schema(description = "Code submitted by the user", example = "public class Main { ... }")
    @OptionalNotBlank
    private String code;

    @Schema(description = "Programming language used", example = "JAVA")
    @IsEnum(enumClass = Language.class)
    private String language;

    @Schema(description = "Status of the submission", example = "PASSED")
    @IsEnum(enumClass = Status.class)
    private String status;

    @Schema(description = "Score percentage (0-100)", example = "85.5")
    @Min(value = 0, message = "Score percent must be at least 0")
    @Max(value = 100, message = "Score percent cannot exceed 100")
    private BigDecimal scorePercent;

}
