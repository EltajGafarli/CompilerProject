package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.SubmissionDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface SubmissionService {
    SubmissionDto getSubmissionById(long id);

    List<SubmissionDto> getAllSubmissions();

    List<SubmissionDto> getAllSubmissionsOfUser(UserDetails userDetails);
}
