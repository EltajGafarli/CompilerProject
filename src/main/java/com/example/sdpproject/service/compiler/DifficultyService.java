package com.example.sdpproject.service.compiler;

import java.util.List;

public interface DifficultyService {
    String addDifficulty(String difficulty);

    List<String> getDifficulties();

    String getDifficultiesByName(String name);
}
