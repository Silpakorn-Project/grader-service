package com.su.ac.th.project.grader.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BaseException {
    private String timestamp;
    private String message;
    private Integer code;
}
