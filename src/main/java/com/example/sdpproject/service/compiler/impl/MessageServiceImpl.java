package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.MessageDto;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.entity.algorithm.Conversation;
import com.example.sdpproject.entity.algorithm.Message;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.repository.compiler.ConversationRepository;
import com.example.sdpproject.repository.compiler.MessageRepository;
import com.example.sdpproject.service.compiler.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;


    @Override
    public MessageDto addMessage(UserDetails userDetails, long conversationId, MessageDto messageDto) {
        Message message = Message
                .builder()
                .message(messageDto.getMessage())
                .build();
        User user = getCurrentUser(userDetails);
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new NotFoundException("Conversation not found"));

        conversation.addMessage(message);
        user.addMessage(message);

        this.messageRepository.save(message);
        return this.messageToMessageDto(message);
    }

    @Override
    public List<MessageDto> getUserMessages(UserDetails userDetails) {
        User currentUser = getCurrentUser(userDetails);
        return currentUser.getMessages()
                .stream()
                .map(this::messageToMessageDto)
                .toList();
    }

    public List<MessageDto> getMessages(UserDetails userDetails) {
        User user = getCurrentUser(userDetails);
        return user
                .getMessages()
                .stream().map(this::messageToMessageDto)
                .toList();
    }

    @Override
    public MessageDto getMessageById(long id) {
        return messageRepository.findById(id)
                .map(this::messageToMessageDto)
                .orElseThrow(
                        () -> new NotFoundException("Message not found")
                );
    }

    @Override
    public List<MessageDto> getMessagesByConversationId(long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(
                () -> new NotFoundException("Conversation not found")
        );



        return conversation.getMessages()
                .stream()
                .map(this::messageToMessageDto)
                .toList();
    }

    @Override
    public List<MessageDto> getMessages() {
        return messageRepository
                .findAll()
                .stream()
                .map(this::messageToMessageDto)
                .toList();
    }

    private MessageDto messageToDto(Message message) {
        return MessageDto
                .builder()
                .message(message.getMessage())
                .id(message.getId())
                .userDto(this.userToDto(message.getSender()))
                .build();
    }


    public MessageDto replyMessage(UserDetails userDetails, long conversationId, long messageId, MessageDto messageDto) {
        Message message = Message
                .builder()
                .message(messageDto.getMessage())
                .build();
        User user = this.getCurrentUser(userDetails);
        Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(() -> new NotFoundException("Conversation not found"));
        Message parentMessage = messageRepository.findById(messageId).orElseThrow(() -> new NotFoundException("Message not found"));
        user.addMessage(message);
        conversation.addMessage(message);
        parentMessage.addMessage(message);
        this.messageRepository.save(message);
        return this.messageToMessageDto(message);

    }

    private MessageDto messageToMessageDto(Message message) {
        return MessageDto
                .builder()
                .id(message.getId())
                .message(message.getMessage())
                .userDto(this.userToDto(
                        message.getSender()
                ))
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


    private User getCurrentUser(UserDetails userDetails) {
        return userRepository
                .findByEmail(
                        userDetails.getUsername()
                )
                .orElseThrow(
                        () -> new NotFoundException("User not found")
                );
    }

    @Override
    public String deleteMessage(UserDetails userDetails, long messageId) {
        messageRepository.deleteById(messageId);
        return "Message Deleted";
    }

    @Override
    public MessageDto updateMessage(long messageId, MessageDto messageDto) {
        Message message = messageRepository.findById(messageId).orElseThrow(
                () -> new NotFoundException("Message not found")
        );

        if (messageDto.getMessage() != null) {
            message.setMessage(messageDto.getMessage());
        }

        messageRepository.save(message);
        messageDto.setId(message.getId());
        return messageDto;
    }
}
