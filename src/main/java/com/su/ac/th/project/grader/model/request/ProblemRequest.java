package com.su.ac.th.project.grader.model.request;

import lombok.Data;

@Data
public class ProblemRequest {

    private String title;
    private String description;
    private String difficulty = "EASY";
    private String type = "STRING";

}
