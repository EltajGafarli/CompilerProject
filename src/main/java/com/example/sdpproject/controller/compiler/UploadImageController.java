package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.service.compiler.SubjectImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/api/images")
@RequiredArgsConstructor
public class UploadImageController {
    private final SubjectImageService subjectImageService;

    @PostMapping(path = "/{subjectId}/upload")
    public ResponseEntity<String> uploadImage(@PathVariable long subjectId, @RequestParam(name = "file") MultipartFile multipartFile) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(subjectImageService.handleFileUpload(subjectId, multipartFile));
    }


    @GetMapping("/{subjectId}")
    public ResponseEntity<String> serveFile(@PathVariable long subjectId) {
        return ResponseEntity.ok(subjectImageService.serveFile(subjectId));
    }
}
