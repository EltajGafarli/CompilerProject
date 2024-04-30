package com.example.sdpproject.service.user;

import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;
import com.example.sdpproject.dto.algorithm.SubmissionDto;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.dto.user.UserRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    UserDto getCurrentUser(UserDetails userDetails);

    UserDto updateUser(UserDetails userDetails, UserRequestDto userRequestDto);

    String deleteUser(UserDetails userDetails);

    Long findSolutionsCount(UserDetails userDetails);

    Long findSubmissionCount(UserDetails userDetails);

    List<SubmissionDto> findSubmissionsByUser(UserDetails userDetails);

    List<AlgorithmResponseDto> findSolvedAlgorithms(UserDetails userDetails);
}