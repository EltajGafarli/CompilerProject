package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.compiler.CompilerApiClient;
import com.example.sdpproject.dto.algorithm.AlgorithmRequest;
import com.example.sdpproject.dto.algorithm.AlgorithmResponse;
import com.example.sdpproject.dto.algorithm.SubmissionRequestDto;
import com.example.sdpproject.dto.algorithm.SubmissionResponseDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.Submission;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.repository.compiler.SubmissionRepository;
import com.example.sdpproject.service.compiler.CompilerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class CompilerServiceImpl implements CompilerService {
    private final CompilerApiClient apiClient;
    private final AlgorithmRepository algorithmRepository;
    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;

    @Value(value = "${secrets.clientId}")
    private String clientId;

    @Value(value = "${secrets.clientSecrets}")
    private String clientSecrets;

    @Override
    public AlgorithmResponse sendSolution(AlgorithmRequest algorithmRequest) {
        algorithmRequest.setClientId(clientId);
        algorithmRequest.setClientSecret(clientSecrets);

        return apiClient.sendAlgorithm(algorithmRequest);
    }


    @Override
    public SubmissionResponseDto submitSolution(UserDetails userDetails, long algorithmId, SubmissionRequestDto submissionRequestDto) {
        Algorithm algorithm = algorithmRepository.findById(algorithmId).orElseThrow(
                () -> new NotFoundException("Algorithm not found!")
        );
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
        var testCases = algorithm.getTestCases();

        long memory = 0L;
        double cpuTime = 0L;

        for (var test : testCases) {
            AlgorithmRequest algorithmRequest = AlgorithmRequest
                    .builder()
                    .language(submissionRequestDto.getProgrammingLanguages().getLanguageCode())
                    .versionIndex(submissionRequestDto.getProgrammingLanguages().getVersionIndex())
                    .script(submissionRequestDto.getSolutionCode())
                    .stdin(test.getTestCase())
                    .build();

            AlgorithmResponse algorithmResponse = sendSolution(algorithmRequest);

            if (algorithmResponse.getMemory() != null && algorithmResponse.getCpuTime() != null) {
                memory += Long.parseLong(algorithmResponse.getMemory());
                cpuTime += Double.parseDouble(algorithmResponse.getCpuTime());
            }

            if (!algorithmResponse.getOutput().equals(test.getCorrectAnswer())) {
                Submission submission = Submission.builder()
                        .cpuTime(Double.toString(cpuTime))
                        .memory(Long.toString(memory))
                        .solutionCode(algorithmRequest.getScript())
                        .programmingLanguage(algorithmRequest.getLanguage())
                        .isSuccessful(false)
                        .algorithm(algorithm)
                        .user(user)
                        .build();

                Submission savedSubmission = submissionRepository.save(submission);
                return submissionToSubmissionDto(savedSubmission);
            }

        }

        Submission submission = Submission.builder()
                .cpuTime(Double.toString(cpuTime))
                .memory(Long.toString(memory))
                .solutionCode(submissionRequestDto.getSolutionCode())
                .programmingLanguage(submissionRequestDto.getProgrammingLanguages().getLanguageCode())
                .isSuccessful(true)
                .algorithm(algorithm)
                .user(user)
                .build();

        Submission savedSubmission = submissionRepository.save(submission);

        return submissionToSubmissionDto(savedSubmission);
    }

    private SubmissionResponseDto submissionToSubmissionDto(Submission submission) {
        return SubmissionResponseDto
                .builder()
                .cpuTime(submission.getCpuTime())
                .isSuccessful(submission.isSuccessful())
                .memory(submission.getMemory())
                .programmingLanguage(submission.getProgrammingLanguage())
                .createdAt(submission.getCreatedAt())
                .solutionCode(submission.getSolutionCode())
                .build();
    }
}
