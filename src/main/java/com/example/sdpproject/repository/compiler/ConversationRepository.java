package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
