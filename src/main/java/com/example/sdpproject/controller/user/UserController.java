package com.example.sdpproject.controller.user;

import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.dto.user.UserRequestDto;
import com.example.sdpproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.getCurrentUser(userDetails)
                );
    }


    @PutMapping
    public ResponseEntity<UserDto> updateUser(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity
                .ok(
                        userService.updateUser(userDetails, userRequestDto)
                );
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.deleteUser(userDetails)
                );
    }

    @GetMapping(path = "/solutioncount")
    public ResponseEntity<Long> getSolutionCount(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.findSolutionsCount(userDetails)
                );
    }

    @GetMapping(path = "/submissioncount")
    public ResponseEntity<Long> getSubmissionCount(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.findSubmissionCount(userDetails)
                );
    }


    @GetMapping(path = "/solvedalgos")
    public ResponseEntity<List<AlgorithmResponseDto>> getSolvedAlgorithmsOfUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.findSolvedAlgorithms(userDetails)
                );
    }


}
