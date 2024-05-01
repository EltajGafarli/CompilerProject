package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.dto.algorithm.SubjectRequestDto;
import com.example.sdpproject.dto.algorithm.SubjectResponseDto;
import com.example.sdpproject.service.compiler.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/subjects")
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping(path = "/create")
    public ResponseEntity<String> createSubject(@RequestBody SubjectRequestDto requestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        this.subjectService.createSubject(requestDto)
                );
    }

    @GetMapping(path = "/{subjectId}/{algorithmId}")
    public ResponseEntity<String> addAlgorithmToSubject(@PathVariable long subjectId, @PathVariable long algorithmId) {
        return ResponseEntity
                .ok(
                        this.subjectService.addAlgorithmToSubject(subjectId, algorithmId)
                );
    }

    @GetMapping(path = "/{subjectId}")
    public ResponseEntity<SubjectResponseDto> getSubjectById(@PathVariable long subjectId) {
        return ResponseEntity
                .ok(
                        subjectService.getSubjectById(subjectId)
                );
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponseDto>> getSubjects() {
        return ResponseEntity
                .ok(
                        subjectService.getSubjects()
                );
    }


    @PutMapping(path = "/{subjectId}")
    public ResponseEntity<SubjectResponseDto> updateSubject(@PathVariable long subjectId, @RequestBody SubjectRequestDto subjectRequestDto) {
        return ResponseEntity
                .ok(
                        subjectService.updateSubject(subjectId, subjectRequestDto)
                );
    }

    @DeleteMapping(path = "/{subjectId}")
    public ResponseEntity<String> deleteSubject(@PathVariable long subjectId) {
        return ResponseEntity
                .ok(
                        subjectService.deleteSubject(subjectId)
                );
    }

}
