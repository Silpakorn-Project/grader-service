package com.su.ac.th.project.grader.model.request.problem;

import com.su.ac.th.project.grader.constant.CommonConstant;
import lombok.Data;

@Data
public class ProblemSearchCriteria {
    private String title;
    private String description;
    private CommonConstant.ProblemDifficulty difficulty;
    private CommonConstant.ProblemType type;

    public void setDifficulty(String difficulty) {
        this.difficulty = CommonConstant.ProblemDifficulty.valueOf(difficulty.toUpperCase());
    }

    public void setType(String type) {
        this.type = CommonConstant.ProblemType.valueOf(type.toUpperCase());
    }
}
