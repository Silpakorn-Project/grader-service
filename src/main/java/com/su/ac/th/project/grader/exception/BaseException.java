package com.su.ac.th.project.grader.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseException {
    private String timestamp;
    private String message;
    private Integer code;
}
