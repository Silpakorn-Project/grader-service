package com.su.ac.th.project.grader.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseModel {

    private LocalDateTime timestamp;
    private String message;
    private String code;
    private Object data;

}
