package com.su.ac.th.project.grader.model.request.problem;

import com.su.ac.th.project.grader.util.validator.enumerated.IsEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static com.su.ac.th.project.grader.constant.CommonConstant.ProblemDifficulty;
import static com.su.ac.th.project.grader.constant.CommonConstant.ProblemType;

@Data
public class ProblemRequest {

    @Schema(description = "Problem ID", example = "1")
    private String problemId;

    @Schema(
            description = "Title of the problem",
            example = "Calculate the Sum of Two Numbers"
    )
    @NotBlank
    private String title;

    @Schema(
            description = "Description of the problem",
            example = "Write a program to calculate the sum of two integers."
    )
    @NotBlank
    private String description;

    @Schema(
            description = "Difficulty level of the problem",
            example = "EASY",
            defaultValue = "EASY"
    )
    @IsEnum(enumClass = ProblemDifficulty.class)
    private String difficulty = String.valueOf(ProblemDifficulty.EASY);

    @Schema(
            description = "Type of the problem",
            example = "MATH",
            defaultValue = "MATH"
    )
    @IsEnum(enumClass = ProblemType.class)
    private String type = String.valueOf(ProblemType.MATH);

}
