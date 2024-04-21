package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.entity.algorithm.Difficulty;
import com.example.sdpproject.repository.compiler.DifficultyRepository;
import com.example.sdpproject.service.compiler.DifficultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
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
}
