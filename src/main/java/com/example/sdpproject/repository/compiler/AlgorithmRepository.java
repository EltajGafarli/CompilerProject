package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Algorithm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmRepository extends JpaRepository<Algorithm, Long> {
}
