package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.ConversationDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface ConversationService {
    ConversationDto addConversation(UserDetails userDetails, ConversationDto conversationDto);

    String deleteConversation(long conversationId);

    ConversationDto getConversationById(long id);

    List<ConversationDto> getConversations();
}
