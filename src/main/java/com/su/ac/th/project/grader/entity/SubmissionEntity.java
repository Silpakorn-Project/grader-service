package com.su.ac.th.project.grader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class SubmissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int submissionId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private double scorePercent;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum Language {
        JAVA, PYTHON, C, C_PLUS_PLUS, JAVASCRIPT, RUBY, OTHER
    }

    public enum Status {
        PENDING, PASSED, FAILED
    }

}
