package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.SubmissionDto;
import com.example.sdpproject.entity.algorithm.Submission;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.repository.compiler.SubmissionRepository;
import com.example.sdpproject.service.compiler.SubmissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class SubmissionServiceImpl implements SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;

    @Override
    public SubmissionDto getSubmissionById(long id) {
        Submission submission = submissionRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Submission not found!")
        );
        return submissionToSubmissionDto(submission);
    }

    @Override
    public List<SubmissionDto> getAllSubmissions() {
        List<Submission> allSubmissions = submissionRepository.findAll();
        return allSubmissions
                .stream()
                .map(this::submissionToSubmissionDto)
                .toList();
    }


    @Override
    public List<SubmissionDto> getAllSubmissionsOfUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found!")
        );
        return user.getSubmissions()
                .stream()
                .map(this::submissionToSubmissionDto)
                .toList();

    }

    private SubmissionDto submissionToSubmissionDto(Submission submission) {
        return SubmissionDto
                .builder()
                .id(submission.getId())
                .programmingLanguages(submission.getProgrammingLanguage())
                .cpuTime(submission.getCpuTime())
                .memory(submission.getMemory())
                .isSuccessful(submission.isSuccessful())
                .solutionCode(submission.getSolutionCode())
                .build();
    }
}
