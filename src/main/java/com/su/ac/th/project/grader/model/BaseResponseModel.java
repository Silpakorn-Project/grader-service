package com.su.ac.th.project.grader.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponseModel {

    private String timestamp;
    private String message;
    private String code;
    private Object data;

}
