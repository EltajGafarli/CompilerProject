package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.AlgorithmRequest;
import com.example.sdpproject.dto.algorithm.AlgorithmResponse;
import com.example.sdpproject.dto.algorithm.SubmissionRequestDto;
import com.example.sdpproject.dto.algorithm.SubmissionResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface CompilerService {
    AlgorithmResponse sendSolution(AlgorithmRequest algorithmRequest);

    SubmissionResponseDto submitSolution(UserDetails userDetails, long algorithmId, SubmissionRequestDto submissionRequestDto);
}
