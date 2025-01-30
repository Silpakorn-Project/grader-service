package com.su.ac.th.project.grader.model.request.testcase;

import com.su.ac.th.project.grader.util.validator.optionalnotblank.OptionalNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TestcasesUpdateRequest {

    @Schema(description = "Problem ID", example = "1")
    private Long problemId;

    @Schema(description = "Input data for the testcase", example = "4 5")
    @OptionalNotBlank
    private String inputData;

    @Schema(description = "Expected output for the given input data", example = "9")
    @OptionalNotBlank
    private String expectedOutput;

}
