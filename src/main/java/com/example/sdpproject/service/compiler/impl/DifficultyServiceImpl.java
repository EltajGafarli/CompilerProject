package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.entity.algorithm.Difficulty;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.DifficultyRepository;
import com.example.sdpproject.service.compiler.DifficultyService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class DifficultyServiceImpl implements DifficultyService {

    private final DifficultyRepository difficultyRepository;
    @Override
    public String addDifficulty(String difficultyLevel) {
        Difficulty difficulty = Difficulty
                .builder()
                .difficultyLevel(difficultyLevel)
                .build();
        difficultyRepository.save(difficulty);
        return "Difficulty added successfully!";
    }

    @Override
    public List<String> getDifficulties() {
        return difficultyRepository.findAll()
                .stream()
                .map(Difficulty::getDifficultyLevel)
                .toList();
    }

    @Override
    public String getDifficultiesByName(String name) {
        Difficulty difficulty = difficultyRepository.findDifficultiesByDifficultyLevel(name).orElseThrow(() -> new NotFoundException("Difficulty Not found!"));
        return difficulty.getDifficultyLevel();
    }
}
