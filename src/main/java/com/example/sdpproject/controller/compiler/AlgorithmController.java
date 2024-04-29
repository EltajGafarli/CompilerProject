package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.dto.algorithm.*;
import com.example.sdpproject.service.compiler.AlgorithmService;
import com.example.sdpproject.service.compiler.AlgorithmTestCaseService;
import com.example.sdpproject.service.compiler.CompilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/algorithms")
@RequiredArgsConstructor
public class AlgorithmController {
    private final AlgorithmService algorithmService;
    private final AlgorithmTestCaseService algorithmTestCaseService;
    private final CompilerService compilerService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createNewAlgorithm(@RequestBody AlgorithmRequestDto algorithmRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        algorithmService.addAlgorithm(algorithmRequestDto)
                );
    }

    @GetMapping
    public ResponseEntity<List<AlgorithmResponseDto>> getAllAlgorithms() {
        return ResponseEntity
                .ok(
                        algorithmService.getAllAlgorithms()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AlgorithmResponseDto> getAlgorithm(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                        algorithmService.getAlgorithm(id)
                );
    }

    @PostMapping(path = "/{id}/addTestCase")
    public ResponseEntity<String> addTestCaseToAlgorithm(@PathVariable long id, @RequestBody AlgorithmTestCaseDto algorithmTestCaseDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        algorithmTestCaseService.addTestCase(id, algorithmTestCaseDto)
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteAlgorithm(@PathVariable long id) {
        return ResponseEntity
                .ok(algorithmService.deleteAlgorithm(id));

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AlgorithmResponseDto> updateAlgorithm(@PathVariable long id, AlgorithmRequestDto algorithmRequestDto) {
        return ResponseEntity
                .ok(
                        algorithmService
                                .updateAlgorithm(id, algorithmRequestDto)
                );
    }


    @PostMapping(path = "/{id}/submit")
    public ResponseEntity<SubmissionResponseDto> submitSolution(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails, SubmissionRequestDto submissionRequestDto) {
        return ResponseEntity
                .ok(
                        compilerService.submitSolution(userDetails, id, submissionRequestDto)
                );
    }


    @GetMapping(path = "/algorithmtag")
    public ResponseEntity<List<AlgorithmResponseDto>> getAlgorithmsByTag(@RequestParam String tag) {
        return ResponseEntity
                .ok(
                        this.algorithmService.getAlgorithmsByAlgorithmTag(tag)
                );
    }

}
