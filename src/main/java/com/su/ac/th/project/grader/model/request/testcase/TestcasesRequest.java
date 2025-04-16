package com.su.ac.th.project.grader.model.request.testcase;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TestcasesRequest {

    @Schema(description = "Problem ID", example = "1")
    @NotNull
    private Long problemId;

    @Schema(description = "List of test cases")
    @NotEmpty
    @Valid
    private List<Testcase> testcases;

    @Data
    public static class Testcase {

        @Schema(description = "Input data for the testcase", example = "2 3")
        @NotBlank
        private String inputData;

        @Schema(description = "Expected output for the given input data", example = "5")
        @NotBlank
        private String expectedOutput;
    }
}
