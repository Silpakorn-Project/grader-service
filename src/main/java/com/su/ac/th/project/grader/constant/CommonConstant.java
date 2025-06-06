package com.su.ac.th.project.grader.constant;

public class CommonConstant {

    public static final String CONTEXT_PATH = "grader-service";

    public enum ProblemType {
        MATH, DATA_STRUCTURE, GRAPH, STRING
    }

    public enum ProblemDifficulty {
        EASY, MEDIUM, HARD
    }

    public enum Language {
        JAVA, PYTHON, C
    }

    public enum Status {
        Passed, Failed
    }

}
