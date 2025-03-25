package com.su.ac.th.project.grader.model.request.submission;

import com.su.ac.th.project.grader.constant.CommonConstant;
import lombok.Data;

@Data
public class SubmissionsSearchCriteria {
    private Long userId;

    private Long problemId;

    private CommonConstant.Language language;

    private CommonConstant.Status status;

}
