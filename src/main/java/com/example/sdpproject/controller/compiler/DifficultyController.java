package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.service.compiler.DifficultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/difficulty")
@RequiredArgsConstructor
public class DifficultyController {
    private final DifficultyService difficultyService;

    @PostMapping
    public ResponseEntity<String> addDifficulty(@RequestParam String difficulty) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        difficultyService.addDifficulty(difficulty)
                );
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllDifficulties() {
        return ResponseEntity
                .ok(
                        difficultyService.getDifficulties()
                );
    }


}
