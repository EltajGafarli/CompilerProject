package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.AlgorithmRequestDto;
import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;
import com.example.sdpproject.dto.algorithm.AlgorithmTestCaseDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.AlgorithmTestCases;
import com.example.sdpproject.entity.algorithm.Difficulty;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.mapper.compiler.AlgorithmMapper;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.repository.compiler.DifficultyRepository;
import com.example.sdpproject.service.compiler.AlgorithmService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class AlgorithmServiceImpl implements AlgorithmService {

    private final AlgorithmRepository algorithmRepository;
    private final AlgorithmMapper algorithmMapper;
    private final DifficultyRepository difficultyRepository;

    @Override
    public String addAlgorithm(AlgorithmRequestDto algorithmRequestDto) {
        Difficulty difficulty = difficultyRepository
                .findDifficultiesByDifficultyLevel(
                        algorithmRequestDto.getDifficultyLevel()
                ).orElseThrow(
                        () -> new NotFoundException("Difficulty not found!")
                );
        Algorithm algorithm = algorithmMapper.dtoToAlgorithm(algorithmRequestDto);

        algorithm.setDifficulty(difficulty);
        algorithmRepository.save(algorithm);
        return "Algorithm added successfully";
    }

    @Override
    public AlgorithmResponseDto getAlgorithm(long id) {
        Algorithm algorithm = algorithmRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Algorithm not found!")
        );
        return algorithmToAlgorithmResponseDto(algorithm);
    }

    @Override
    public List<AlgorithmResponseDto> getAllAlgorithms() {
        return algorithmRepository
                .findAll()
                .stream()
                .map(this::algorithmToAlgorithmResponseDto)
                .toList();
    }

    @Override
    public String deleteAlgorithm(long id) {
        this.algorithmRepository.deleteById(id);
        return "Algorithm deleted successfully";
    }

    @Override
    public AlgorithmResponseDto updateAlgorithm(long id, AlgorithmRequestDto algorithmRequestDto) {
        Algorithm algorithm = algorithmRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Algorithm not found!")
        );
        if (algorithmRequestDto.getTitle() != null) {
            algorithm.setTitle(algorithmRequestDto.getTitle());
        }

        if (algorithmRequestDto.getConstraints() != null) {
            algorithm.setConstraints(algorithmRequestDto.getConstraints());
        }

        if (algorithmRequestDto.getProblemStatement() != null) {
            algorithm.setProblemStatement(algorithmRequestDto.getProblemStatement());
        }

        if (algorithmRequestDto.getDifficultyLevel() != null) {
            Difficulty difficulty = difficultyRepository.findDifficultiesByDifficultyLevel(algorithmRequestDto.getDifficultyLevel()).orElseThrow(
                    () -> new NotFoundException("Difficulty not found")
            );
            algorithm.setDifficulty(difficulty);
        }


        Algorithm savedAlgorithm = algorithmRepository.save(algorithm);


        return algorithmToAlgorithmResponseDto(savedAlgorithm);
    }

    private AlgorithmResponseDto algorithmToAlgorithmResponseDto(Algorithm algorithm) {
        List<AlgorithmTestCaseDto> algorithmTestCaseDtos = algorithm.getTestCases().stream()
                .map(this::testCaseToDto)
                .toList();

        return AlgorithmResponseDto
                .builder()
                .title(algorithm.getTitle())
                .problemStatement(algorithm.getProblemStatement())
                .constraints(algorithm.getConstraints())
                .id(algorithm.getId())
                .difficulty(algorithm.getDifficulty().getDifficultyLevel())
                .testCases(algorithmTestCaseDtos)
                .build();
    }

    private AlgorithmTestCaseDto testCaseToDto(AlgorithmTestCases algorithmTestCases) {
        return AlgorithmTestCaseDto
                .builder()
                .testCase(algorithmTestCases.getTestCase())
                .correctAnswer(algorithmTestCases.getCorrectAnswer())
                .build();
    }


}
