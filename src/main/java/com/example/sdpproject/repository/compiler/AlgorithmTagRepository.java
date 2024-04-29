package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.AlgorithmTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlgorithmTagRepository extends JpaRepository<AlgorithmTag, Long> {
    Optional<AlgorithmTag> findAlgorithmTagByAlgorithmTag(String algorithmTag);
}
