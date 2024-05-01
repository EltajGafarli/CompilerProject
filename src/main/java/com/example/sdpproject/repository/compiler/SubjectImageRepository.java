package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.SubjectImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectImageRepository extends JpaRepository<SubjectImage, Long> {
}
