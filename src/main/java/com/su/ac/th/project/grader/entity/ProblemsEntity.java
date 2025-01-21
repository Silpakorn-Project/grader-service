package com.su.ac.th.project.grader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "problems")
public class ProblemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemDifficulty difficulty = ProblemDifficulty.EASY;

    public enum ProblemDifficulty {
        EASY, MEDIUM, HARD
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemType type = ProblemType.STRING;

    public enum ProblemType {
        MATH, DATA_STRUCTURE, GRAPH, STRING
    }

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

}
