package com.su.ac.th.project.grader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "submissions")
public class SubmissionsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long submissionId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Status status = Status.Pending;

    @Column(nullable = false)
    private BigDecimal scorePercent;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum Language {
        JAVA, PYTHON, C
    }

    public enum Status {
        Pending, Passed, Failed
    }

}
