package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
