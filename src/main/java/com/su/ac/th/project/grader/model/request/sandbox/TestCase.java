package com.su.ac.th.project.grader.model.request.sandbox;

import com.su.ac.th.project.grader.model.response.TestcasesResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCase {
    private String input;
    private String expected_output;

    public TestCase(TestcasesResponse other) {
        this.input = other.getInputData();
        this.expected_output = other.getExpectedOutput();
    }
}
