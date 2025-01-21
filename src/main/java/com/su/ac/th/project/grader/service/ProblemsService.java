package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.ProblemsEntity;
import com.su.ac.th.project.grader.model.response.ProblemsResponse;
import com.su.ac.th.project.grader.repository.jpa.ProblemsRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProblemsService {

    private final ProblemsRepository problemsRepository;

    public ProblemsService(ProblemsRepository problemsRepository) {
        this.problemsRepository = problemsRepository;
    }

    public List<ProblemsResponse> getAllProblems() {

        List<ProblemsEntity> p = problemsRepository.findAll();
        List<ProblemsResponse> data = DtoEntityMapper.mapListToDto(p, ProblemsResponse.class);

        return data;

    }

    public ProblemsResponse getProblemById(Long id) {
        ProblemsEntity p = problemsRepository.findById(id).orElseThrow(
                () -> new RuntimeException("id not found"));
        ProblemsResponse data = DtoEntityMapper.mapToDto(p, ProblemsResponse.class);

        return data;
    }

}
