package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.entity.algorithm.AlgorithmTag;
import com.example.sdpproject.exception.AlreadyExistException;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.AlgorithmTagRepository;
import com.example.sdpproject.service.compiler.AlgorithmTagService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class AlgorithmTagServiceImpl implements AlgorithmTagService {

    private final AlgorithmTagRepository algorithmTagRepository;

    @Override
    public String addAlgorithmTag(String difficultyLevel) {
        algorithmTagRepository.findAlgorithmTagByAlgorithmTag(difficultyLevel).ifPresent(
                data -> {
                    throw new AlreadyExistException("Tag already exist!");
                }

        );
        AlgorithmTag algorithmTag = AlgorithmTag
                .builder()
                .algorithmTag(difficultyLevel)
                .build();
        algorithmTagRepository.save(algorithmTag);
        return "Difficulty added successfully!";
    }

    @Override
    public List<String> getAlgorithmTags() {
        return algorithmTagRepository.findAll()
                .stream()
                .map(AlgorithmTag::getAlgorithmTag)
                .toList();
    }

    @Override
    public String getAlgorithmTagByName(String name) {
        AlgorithmTag algorithmTag = algorithmTagRepository.findAlgorithmTagByAlgorithmTag(name).orElseThrow(() -> new NotFoundException("AlgorithmTag Not found!"));
        return algorithmTag.getAlgorithmTag();
    }

    @Override
    public String updateAlgorithmTag(String name, String newTagName) {
        AlgorithmTag algorithmTag = algorithmTagRepository.findAlgorithmTagByAlgorithmTag(name)
                .orElseThrow(
                        () -> new NotFoundException("AlgorithmTag not found")
                );

        if (newTagName != null) {
            algorithmTag.setAlgorithmTag(newTagName);
        }
        algorithmTagRepository.save(algorithmTag);
        return "Algorithm Tag updated successfully";
    }

    @Override
    @Transactional
    public String deleteAlgorithmTag(String tag) {
        AlgorithmTag algorithmTag = algorithmTagRepository.findAlgorithmTagByAlgorithmTag(tag)
                .orElseThrow(
                        () -> new NotFoundException("Algorithm Not found")
                );

        algorithmTagRepository.delete(algorithmTag);
        this.algorithmTagRepository.flush();
        return "Algorithm tag deleted";
    }
}
