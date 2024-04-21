package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DifficultyRepository extends JpaRepository<Difficulty, Long> {
    Optional<Difficulty> findDifficultiesByDifficultyLevel(String difficultyLevel);
}
