package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.AlgorithmImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgorithmImageRepository extends JpaRepository<AlgorithmImage, Long> {
}
