package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import com.example.sdpproject.repository.compiler.ConversationRepository;
import com.example.sdpproject.repository.compiler.MessageRepository;
import com.example.sdpproject.service.compiler.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final UserRepository userRepository;
    private AlgorithmRepository algorithmRepository;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
}
