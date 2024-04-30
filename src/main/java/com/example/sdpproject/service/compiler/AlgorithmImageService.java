package com.example.sdpproject.service.compiler;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

public interface AlgorithmImageService {
    String handleFileUpload(long algorithmId, MultipartFile file);

    Set<String> serveFile(long algorithmId);
}
