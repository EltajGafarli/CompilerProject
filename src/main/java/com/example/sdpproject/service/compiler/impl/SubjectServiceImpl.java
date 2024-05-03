package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;
import com.example.sdpproject.dto.algorithm.AlgorithmTestCaseDto;
import com.example.sdpproject.dto.algorithm.SubjectRequestDto;
import com.example.sdpproject.dto.algorithm.SubjectResponseDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.AlgorithmTag;
import com.example.sdpproject.entity.algorithm.AlgorithmTestCases;
import com.example.sdpproject.entity.algorithm.Subject;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.repository.compiler.AlgorithmTagRepository;
import com.example.sdpproject.repository.compiler.SubjectRepository;
import com.example.sdpproject.service.compiler.SubjectService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final AlgorithmRepository algorithmRepository;
    private final AlgorithmTagRepository algorithmTagRepository;


    @Override
    public String createSubject(SubjectRequestDto requestDto) {
        AlgorithmTag algorithmTag = algorithmTagRepository.findAlgorithmTagByAlgorithmTag(requestDto.getAlgorithmTag()).orElseThrow(
                () -> new NotFoundException("Tag not found")
        );

        Subject subject = this.requestToSubject(requestDto);
        subject.setAlgorithmTag(algorithmTag);
        subjectRepository.save(subject);

        return "Subject created successfully";
    }

    @Override
    public String addAlgorithmToSubject(long subjectId, long algorithmId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Subject not found")
        );

        Algorithm algorithm = algorithmRepository.findById(algorithmId).orElseThrow(
                () -> new NotFoundException("Algorithm not found")
        );

        subject.addAlgorithm(algorithm);
        algorithmRepository.save(algorithm);
        return "Algorithm added to Subject";
    }

    @Override
    public SubjectResponseDto getSubjectById(long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(
                        () -> new NotFoundException("Subject not found")
                );
        List<AlgorithmResponseDto> algorithmResponseDtos = subject.getAlgorithms()
                .stream().map(this::algorithmToDto)
                .toList();

        return SubjectResponseDto
                .builder()
                .subjectId(subject.getId())
                .rank(subject.getRank())
                .description(subject.getDescription())
                .title(subject.getTitle())
                .algorithmTag(subject.getAlgorithmTag().getAlgorithmTag())
                .algorithms(algorithmResponseDtos)
                .build();
    }

    @Override
    public List<SubjectResponseDto> getSubjects() {
        return subjectRepository.findAll().stream()
                .map(this::subjectToSubjectResponse)
                .toList();
    }

    @Override
    public String deleteSubject(long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(
                        () -> new NotFoundException("Subject not found")
                );

        subject.getAlgorithmTag().setSubject(null);
        this.subjectRepository.delete(subject);
        this.subjectRepository.flush();

        return "Subject deleted successfully";
    }

    @Override
    public SubjectResponseDto updateSubject(long subjectId, SubjectRequestDto requestDto) {
        Subject subject = this.subjectRepository.findById(subjectId)
                .orElseThrow(
                        () -> new NotFoundException("Subject not found")
                );


        if(requestDto.getDescription() != null) {
            subject.setDescription(requestDto.getDescription());
        }

        if (requestDto.getRank() != null) {
            subject.setRank(requestDto.getRank());
        }

        if(requestDto.getTitle() != null) {
            subject.setTitle(requestDto.getTitle());
        }

        if(requestDto.getAlgorithmTag() != null) {
            subject.setAlgorithmTag(
                    algorithmTagRepository.findAlgorithmTagByAlgorithmTag(
                            requestDto.getAlgorithmTag()
                    ).orElseThrow(
                            () -> new NotFoundException("Tag not found")
                    )
            );
        }

        Subject savedSubject = subjectRepository.save(subject);

        return this.subjectToSubjectResponse(savedSubject);
    }


    private Subject requestToSubject(SubjectRequestDto requestDto) {
        return Subject
                .builder()
                .title(requestDto.getTitle())
                .rank(requestDto.getRank())
                .description(requestDto.getDescription())
                .build();
    }

    private AlgorithmResponseDto algorithmToDto(Algorithm algorithm) {
        return AlgorithmResponseDto
                .builder()
                .id(algorithm.getId())
                .difficulty(algorithm.getAlgorithmTag().getAlgorithmTag())
                .constraints(algorithm.getConstraints())
                .problemStatement(algorithm.getProblemStatement())
                .title(algorithm.getTitle())
                .testCases(
                        algorithm
                                .getTestCases()
                                .stream().map(
                                        this::testCaseToTestCaseDto
                                )
                                .toList()
                )
                .build();
    }
    private AlgorithmTestCaseDto testCaseToTestCaseDto(AlgorithmTestCases algorithmTestCases) {
        return AlgorithmTestCaseDto
                .builder()
                .testCase(algorithmTestCases.getTestCase())
                .correctAnswer(algorithmTestCases.getCorrectAnswer())
                .build();
    }


    private SubjectResponseDto subjectToSubjectResponse(Subject subject) {
        return SubjectResponseDto
                .builder()
                .subjectId(subject.getId())
                .title(subject.getTitle())
                .rank(subject.getRank())
                .description(subject.getDescription())
                .algorithmTag(subject.getAlgorithmTag().getAlgorithmTag())
                .algorithms(
                        subject.getAlgorithms().stream()
                                .map(this::algorithmToDto)
                                .toList()
                )
                .build();
    }
}
