package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Submission;
import com.example.sdpproject.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findSubmissionByUserOrderByCreatedAtDesc(User user);
}
