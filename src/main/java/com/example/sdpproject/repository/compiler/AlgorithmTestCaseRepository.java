package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.AlgorithmTestCases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmTestCaseRepository extends JpaRepository<AlgorithmTestCases, Long> {
}
