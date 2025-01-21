package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.ProblemsEntity;
import com.su.ac.th.project.grader.model.response.ProblemsResponse;
import com.su.ac.th.project.grader.repository.jpa.ProblemsRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.su.ac.th.project.grader.exception.BusinessException.notFound;

@Service
public class ProblemsService {

    private final ProblemsRepository problemsRepository;

    public ProblemsService(ProblemsRepository problemsRepository) {
        this.problemsRepository = problemsRepository;
    }

    public List<ProblemsResponse> getAllProblems() {

        List<ProblemsEntity> p = problemsRepository.findAll();

        return DtoEntityMapper.mapListToDto(p, ProblemsResponse.class);

    }

    public ProblemsResponse getProblemById(Long id) {

        ProblemsEntity p = problemsRepository
                .findById(id)
                .orElseThrow(() -> notFound(String.valueOf(id)));

        return DtoEntityMapper.mapToDto(p, ProblemsResponse.class);
    }

}
