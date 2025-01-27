package com.su.ac.th.project.grader.service.external.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestCase {
    private String input;
    private String expected_output;
}
