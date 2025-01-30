package com.su.ac.th.project.grader.model.request;

import com.su.ac.th.project.grader.util.validator.enumerated.IsEnum;
import com.su.ac.th.project.grader.util.validator.optionalnotblank.OptionalNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static com.su.ac.th.project.grader.constant.CommonConstant.ProblemDifficulty;
import static com.su.ac.th.project.grader.constant.CommonConstant.ProblemType;

@Data
public class ProblemUpdateRequest {

    @Schema(description = "Problem ID", example = "1")
    @NotNull
    private Long problemId;

    @Schema(
            description = "Title of the problem",
            example = "Calculate the Sum of Two Numbers"
    )
    @OptionalNotBlank
    private String title;

    @Schema(
            description = "Description of the problem",
            example = "Write a program to calculate the sum of two integers."
    )
    @OptionalNotBlank
    private String description;

    @Schema(
            description = "Difficulty level of the problem",
            example = "HARD",
            defaultValue = "EASY"
    )
    @IsEnum(enumClass = ProblemDifficulty.class)
    private String difficulty = String.valueOf(ProblemDifficulty.EASY);

    @Schema(
            description = "Type of the problem",
            example = "STRING",
            defaultValue = "MATH"
    )
    @IsEnum(enumClass = ProblemType.class)
    private String type = String.valueOf(ProblemType.MATH);

}
