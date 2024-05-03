package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.MessageDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MessageService {
    String deleteMessage(UserDetails userDetails, long messageId);

    MessageDto updateMessage(long messageId, MessageDto messageDto);

    MessageDto addMessage(UserDetails userDetails, long conversationId, MessageDto messageDto);

    List<MessageDto> getUserMessages(UserDetails userDetails);

    List<MessageDto> getMessages();

    MessageDto getMessageById(long id);

    List<MessageDto> getMessagesByConversationId(long conversationId);

    MessageDto replyMessage(UserDetails userDetails, long conversationId, long messageId, MessageDto messageDto);
}
