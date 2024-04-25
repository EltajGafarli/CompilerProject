package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
