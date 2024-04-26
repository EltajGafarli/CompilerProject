package com.example.sdpproject.repository.compiler;

import com.example.sdpproject.entity.algorithm.ConversationTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConversationTopicRepository extends JpaRepository<ConversationTopic, Long> {
    Optional<ConversationTopic> findConversationTopicByTopic(String topic);
}
