package com.su.ac.th.project.grader.model.request;

import com.su.ac.th.project.grader.util.validator.enumerated.EnumValidator;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import static com.su.ac.th.project.grader.constant.CommonConstant.ProblemDifficulty;
import static com.su.ac.th.project.grader.constant.CommonConstant.ProblemType;

@Data
public class ProblemRequest {

    @Schema(
            description = "Title of the problem",
            example = "Calculate the Sum of Two Numbers"
    )
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Schema(
            description = "Description of the problem",
            example = "Write a program to calculate the sum of two integers."
    )
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Schema(
            description = "Difficulty level of the problem",
            example = "EASY",
            defaultValue = "EASY"
    )
    @EnumValidator(enumClass = ProblemDifficulty.class)
    private String difficulty = String.valueOf(ProblemDifficulty.EASY);

    @Schema(
            description = "Type of the problem",
            example = "MATH",
            defaultValue = "MATH"
    )
    @EnumValidator(enumClass = ProblemType.class)
    private String type = String.valueOf(ProblemType.MATH);

}
