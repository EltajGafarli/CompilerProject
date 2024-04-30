package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.entity.algorithm.AlgorithmImage;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.AlgorithmImageRepository;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.service.compiler.AlgorithmImageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class AlgorithmImageServiceImpl implements AlgorithmImageService {
    private final Path rootLocation = Paths.get("src/main/resources/static");
    private final AlgorithmRepository algorithmRepository;
    private final AlgorithmImageRepository algorithmImageRepository;

    @Override
    public String handleFileUpload(long algorithmId, MultipartFile file) {
        Algorithm algorithm = algorithmRepository.findById(algorithmId)
                .orElseThrow(() -> new NotFoundException("Algorithm not found"));

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

            AlgorithmImage algorithmImage = AlgorithmImage.builder()
                    .imageUrl(filename)
                    .algorithm(algorithm)
                    .build();

            algorithm.addImage(algorithmImage);
            algorithmImageRepository.save(algorithmImage);

            return "/files/" + filename;
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file due to: " + e.getMessage(), e);
        }
    }

    public Set<String> serveFile(long algorithmId) {
        Algorithm algorithm = this.algorithmRepository.findById(algorithmId)
                .orElseThrow(() -> new NotFoundException("Algorithm not found"));

        Set<AlgorithmImage> algorithmImages = algorithm.getAlgorithmImages();
        Set<String> urls = new HashSet<>();

        for (var image : algorithmImages) {
            urls.add(image.getImageUrl());
        }

        return urls;
    }
}
