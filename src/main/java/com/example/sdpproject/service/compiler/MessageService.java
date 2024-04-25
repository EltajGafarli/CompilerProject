package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.MessageDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MessageService {

    MessageDto addMessage(UserDetails userDetails, long conversationId, MessageDto messageDto);
    List<MessageDto> getMessages(UserDetails userDetails);
    MessageDto replyMessage(UserDetails userDetails, long conversationId, long messageId, MessageDto messageDto);
}
