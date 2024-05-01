package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.entity.algorithm.*;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.*;
import com.example.sdpproject.service.compiler.SubjectImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class SubjectImageServiceImpl implements SubjectImageService {
    private final Path rootLocation = Paths.get("src/main/resources/static");
    private final SubjectRepository subjectRepository;
    private final SubjectImageRepository subjectImageRepository;

    @Override
    public String handleFileUpload(long subjectId, MultipartFile file) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        if (file.isEmpty()) {
            throw new RuntimeException("Cannot upload an empty file.");
        }

        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path destinationFile = this.rootLocation.resolve(filename)
                    .normalize().toAbsolutePath();

            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new SecurityException("Cannot store file outside current directory.");
            }

            file.transferTo(destinationFile);

            SubjectImage subjectImage = SubjectImage.builder()
                    .imageUrl(filename)
                    .subject(subject)
                    .build();

            subject.setSubjectImage(subjectImage);
            subjectImageRepository.save(subjectImage);

            return "/files/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file due to: " + e.getMessage(), e);
        }
    }

    public String serveFile(long subjectId) {
        Subject subject = this.subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found"));

        SubjectImage subjectImage = subject.getSubjectImage();

        return subjectImage.getImageUrl();
    }
}
