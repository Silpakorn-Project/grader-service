package com.su.ac.th.project.grader.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseModel {

    private String timestamp;
    private String message;
    private Integer code;
    private Object data;
    private Integer offset;
    private Integer limit;
    private Long totalRecords;
    private Integer totalPages;
    private Integer dataCount;
}
