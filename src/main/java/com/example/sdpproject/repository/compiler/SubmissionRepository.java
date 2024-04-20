package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
