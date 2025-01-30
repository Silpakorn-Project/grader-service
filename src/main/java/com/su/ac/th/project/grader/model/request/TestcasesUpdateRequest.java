package com.su.ac.th.project.grader.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TestcasesUpdateRequest {

    @Schema(description = "Test case ID", example = "1")
    @NotNull(message = "Testcase id is required")
    private Long testcaseId;

    @Schema(description = "Problem ID", example = "1")
    private Long problemId;

    @Schema(description = "Input data for the testcase", example = "4 5")
    @NotBlank(message = "Input data cannot be empty")
    private String inputData;

    @Schema(description = "Expected output for the given input data", example = "9")
    @NotBlank(message = "Expected output cannot be empty")
    private String expectedOutput;

}
