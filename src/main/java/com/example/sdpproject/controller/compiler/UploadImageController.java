package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.service.compiler.AlgorithmImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;


@RestController
@RequestMapping(path = "/api/images")
@RequiredArgsConstructor
public class UploadImageController {
    private final AlgorithmImageService algorithmImageService;

    @PostMapping(path = "/{algorithmId}/upload")
    public ResponseEntity<String> uploadImage(@PathVariable long algorithmId, @RequestParam(name = "file") MultipartFile multipartFile) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(algorithmImageService.handleFileUpload(algorithmId, multipartFile));
    }


    @GetMapping("/{algorithmId}")
    public ResponseEntity<Set<String>> serveFile(@PathVariable long algorithmId) {
        return ResponseEntity.ok(algorithmImageService.serveFile(algorithmId));
    }
}
