package com.su.ac.th.project.grader.model.request.testcase;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestcasesRequest {

    @Schema(description = "Testcase ID", example = "1")
    private Long testcaseId;

    @Schema(description = "Problem ID", example = "1")
    @NotNull
    private Long problemId;

    @Schema(description = "Input data for the testcase", example = "2 3")
    @NotBlank
    private String inputData;

    @Schema(description = "Expected output for the given input data", example = "5")
    @NotBlank
    private String expectedOutput;

}
