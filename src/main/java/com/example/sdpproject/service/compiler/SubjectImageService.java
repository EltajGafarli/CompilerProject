package com.example.sdpproject.service.compiler;

import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface SubjectImageService {
    String handleFileUpload(long subjectId, MultipartFile file);
    String serveFile(long subjectId);
}
