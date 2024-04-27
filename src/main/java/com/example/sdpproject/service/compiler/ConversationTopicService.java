package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.ConversationTopicDto;

import java.util.List;

public interface ConversationTopicService {
    ConversationTopicDto addTopic(ConversationTopicDto conversationTopicDto);

    String deleteConversationTopic(long id);

    ConversationTopicDto updateConversationTopic(long id, ConversationTopicDto conversationTopicDto);

    ConversationTopicDto getConversationTopicById(long topicId);

    List<ConversationTopicDto> getConversationTopics();
}
