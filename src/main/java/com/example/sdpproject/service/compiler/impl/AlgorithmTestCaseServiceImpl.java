package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.AlgorithmTestCaseDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.AlgorithmTestCases;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.repository.compiler.AlgorithmTestCaseRepository;
import com.example.sdpproject.service.compiler.AlgorithmTestCaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class AlgorithmTestCaseServiceImpl implements AlgorithmTestCaseService {
    private final AlgorithmTestCaseRepository algorithmTestCaseRepository;
    private final AlgorithmRepository algorithmRepository;

    @Override
    public String addTestCase(long algorithmId, AlgorithmTestCaseDto algorithmTestCaseDto) {
        Algorithm algorithm = algorithmRepository.findById(algorithmId).orElseThrow(
                () -> new NotFoundException("Algorithm not found!")
        );
        AlgorithmTestCases algorithmTestCases = AlgorithmTestCases
                .builder()
                .testCase(algorithmTestCaseDto.getTestCase())
                .correctAnswer(algorithmTestCaseDto.getCorrectAnswer())
                .build();

        algorithm.addTestCase(algorithmTestCases);
        algorithmTestCaseRepository.save(algorithmTestCases);
        return "Algorithm test case added successfully";
    }

}
