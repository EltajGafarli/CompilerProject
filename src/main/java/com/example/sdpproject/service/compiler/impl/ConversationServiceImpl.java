package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.ConversationDto;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.entity.algorithm.Conversation;
import com.example.sdpproject.entity.algorithm.ConversationTopic;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.repository.compiler.ConversationRepository;
import com.example.sdpproject.repository.compiler.ConversationTopicRepository;
import com.example.sdpproject.repository.compiler.MessageRepository;
import com.example.sdpproject.service.compiler.ConversationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ConversationServiceImpl implements ConversationService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final ConversationTopicRepository conversationTopicRepository;

    @Override
    public ConversationDto addConversation(UserDetails userDetails, ConversationDto conversationDto) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        ConversationTopic conversationTopic = conversationTopicRepository.findConversationTopicByTopic(conversationDto.getTopicName()).orElseThrow(
                () -> new NotFoundException("Conversation Topic Not found")
        );

        Conversation conversation = Conversation
                .builder()
                .conversationName(conversationDto.getConversationName())
                .conversationTopic(conversationTopic)
                .build();

        conversationTopic.setConversation(conversation);
        Conversation save = conversationRepository.save(conversation);
        user.addConversation(conversation);
        return ConversationDto
                .builder()
                .id(conversation.getId())
                .conversationName(save.getConversationName())
                .topicName(conversationTopic.getTopic())
                .userDto(this.userToDto(conversation.getUser()))
                .build();

    }

    @Override
    public String deleteConversation(long conversationId) {
        conversationRepository.deleteById(conversationId);
        return "Conversation Deleted";
    }


    public ConversationDto getConversationById(long id) {
        Conversation conversation = conversationRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Conversation not found")
        );
        return ConversationDto
                .builder()
                .id(conversation.getId())
                .conversationName(conversation.getConversationName())
                .topicName(conversation.getConversationTopic().getTopic())
                .userDto(this.userToDto(conversation.getUser()))
                .build();
    }

    private UserDto userToDto(User user) {
        return UserDto
                .builder()
                .userName(user.getNameOfUser())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }

    @Override
    public List<ConversationDto> getConversations() {
        return conversationRepository.findAll()
                .stream()
                .map(this::conversationToConversationDto)
                .toList();
    }

    private ConversationDto conversationToConversationDto(Conversation conversation) {
        return ConversationDto
                .builder()
                .topicName(conversation.getConversationTopic().getTopic())
                .conversationName(conversation.getConversationName())
                .id(conversation.getId())
                .userDto(this.userToDto(conversation.getUser()))
                .build();
    }
}
