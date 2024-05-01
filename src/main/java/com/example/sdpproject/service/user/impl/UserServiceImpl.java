package com.example.sdpproject.service.user.impl;

import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;
import com.example.sdpproject.dto.algorithm.AlgorithmTestCaseDto;
import com.example.sdpproject.dto.algorithm.SubmissionDto;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.dto.user.UserRequestDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.AlgorithmTestCases;
import com.example.sdpproject.entity.algorithm.Submission;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.repository.compiler.SubmissionRepository;
import com.example.sdpproject.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SubmissionRepository submissionRepository;

    @Override
    public UserDto getCurrentUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
        return userToUserDto(user);
    }

    @Override
    public UserDto updateUser(UserDetails userDetails, UserRequestDto userRequestDto) {
        User currentUser = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        if (userRequestDto.getFirstName() != null) {
            currentUser.setFirstName(
                    userRequestDto.getFirstName()
            );
        }

        if (userRequestDto.getLastName() != null) {
            currentUser.setLastName(userRequestDto.getLastName());
        }

        if (userRequestDto.getEmail() != null) {
            currentUser.setEmail(userRequestDto.getEmail());
        }

        if (userRequestDto.getPassword() != null) {
            currentUser.setPassword(
                    passwordEncoder.encode(userRequestDto.getPassword())
            );
        }

        userRepository.save(currentUser);
        return userToUserDto(currentUser);

    }

    @Override
    public String deleteUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        


        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public Long findSolutionsCount(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        return user.getSubmissions().stream()
                .filter(Submission::isSuccessful).count();
    }

    @Override
    public Long findSubmissionCount(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        return (long) user.getSubmissions().size();
    }

    @Override
    public List<SubmissionDto> findSubmissionsByUser(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        List<Submission> submissions = submissionRepository.findSubmissionByUserOrderByCreatedAtDesc(user);
        return submissions.stream().map(this::submissionToSubmissionDto).toList();
    }

    @Override
    public List<AlgorithmResponseDto> findSolvedAlgorithms(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        List<Algorithm> algorithms = submissionRepository.findSubmissionByUserOrderByCreatedAtDesc(user)
                .stream()
                .filter(Submission::isSuccessful)
                .map(
                        Submission::getAlgorithm
                ).toList();

        return Set.copyOf(algorithms).stream()
                .map(this::algorithmToAlgorithmResponseDto).toList();
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


    private UserDto userToUserDto(User user) {
        return UserDto
                .builder()
                .userName(user.getNameOfUser())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roleName(user
                        .getRoles()
                        .stream()
                        .map(
                                role -> role.getRole().name()
                        )
                        .toList()
                )
                .build();
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