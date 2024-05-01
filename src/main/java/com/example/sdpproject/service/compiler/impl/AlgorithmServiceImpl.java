package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.AlgorithmRequestDto;
import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;
import com.example.sdpproject.dto.algorithm.AlgorithmTestCaseDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.AlgorithmTag;
import com.example.sdpproject.entity.algorithm.AlgorithmTestCases;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.mapper.compiler.AlgorithmMapper;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.repository.compiler.AlgorithmTagRepository;
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
    private final AlgorithmTagRepository algorithmTagRepository;

    @Override
    public String addAlgorithm(AlgorithmRequestDto algorithmRequestDto) {
        AlgorithmTag algorithmTag = algorithmTagRepository
                .findAlgorithmTagByAlgorithmTag(
                        algorithmRequestDto.getDifficultyLevel()
                ).orElseThrow(
                        () -> new NotFoundException("Difficulty not found!")
                );
        Algorithm algorithm = algorithmMapper.dtoToAlgorithm(algorithmRequestDto);

        algorithm.setAlgorithmTag(algorithmTag);
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
    @Transactional
    public String deleteAlgorithm(long id) {
        Algorithm algorithm = this.algorithmRepository.findById(id).orElseThrow(() -> new NotFoundException("Algorithm not found"));
        if (algorithm.getAlgorithmTag() != null) {
            algorithm.getAlgorithmTag().setAlgorithms(null);
        }

        algorithmRepository.delete(algorithm);
        this.algorithmRepository.flush();
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
            AlgorithmTag algorithmTag = algorithmTagRepository.findAlgorithmTagByAlgorithmTag(algorithmRequestDto.getDifficultyLevel()).orElseThrow(
                    () -> new NotFoundException("Difficulty not found")
            );
            algorithm.setAlgorithmTag(algorithmTag);
        }


        Algorithm savedAlgorithm = algorithmRepository.save(algorithm);


        return algorithmToAlgorithmResponseDto(savedAlgorithm);
    }

    @Override
    public List<AlgorithmResponseDto> getAlgorithmsByAlgorithmTag(String tag) {
        AlgorithmTag algorithmTag = algorithmTagRepository.findAlgorithmTagByAlgorithmTag(tag)
                .orElseThrow(
                        () -> new NotFoundException("Algorithm Not found Exception!")
                );

        return algorithmTag
                .getAlgorithms()
                .stream()
                .map(this::algorithmToAlgorithmResponseDto)
                .toList();

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
                .difficulty(algorithm.getAlgorithmTag().getAlgorithmTag())
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
