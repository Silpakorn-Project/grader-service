package com.su.ac.th.project.grader.model.request.sandbox;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RunTestRequest {
    private String source_code;
    private List<TestCase> test_cases;
}
