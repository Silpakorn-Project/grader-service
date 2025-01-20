package com.su.ac.th.project.grader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "testcases")
public class TestcasesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long testcaseId;

    @Column(nullable = false)
    private Long problemId;

    @Column(nullable = false)
    private String inputData;

    @Column(nullable = false)
    private String expectedOutput;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

}
