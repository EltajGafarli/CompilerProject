package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.dto.algorithm.ConversationTopicDto;
import com.example.sdpproject.entity.algorithm.ConversationTopic;
import com.example.sdpproject.exception.AlreadyExistException;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.repository.compiler.ConversationTopicRepository;
import com.example.sdpproject.service.compiler.ConversationTopicService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class ConversationTopicServiceImpl implements ConversationTopicService {
    private final ConversationTopicRepository conversationTopicRepository;

    @Override
    public ConversationTopicDto addTopic(ConversationTopicDto conversationTopicDto) {
        conversationTopicRepository.findConversationTopicByTopic(conversationTopicDto.getTopic()).ifPresent(
                (conversationTopic) -> new AlreadyExistException("Conversation Already Exist")
        );
        ConversationTopic conversationTopic = ConversationTopic
                .builder()
                .topic(conversationTopicDto.getTopic())
                .build();

        ConversationTopic savedConversationTopic = conversationTopicRepository.save(conversationTopic);
        conversationTopicDto.setId(savedConversationTopic.getId());
        return conversationTopicDto;

    }

    @Override
    public String deleteConversationTopic(long id) {
        conversationTopicRepository.deleteById(id);
        return "Deleted Successfully";
    }


    @Override
    public ConversationTopicDto updateConversationTopic(long id, ConversationTopicDto conversationTopicDto) {
        ConversationTopic conversationTopic = conversationTopicRepository.findById(id).orElseThrow(() -> new NotFoundException("Conversation Not Found"));
        if (conversationTopicDto.getTopic() != null) {
            conversationTopic.setTopic(conversationTopicDto.getTopic());
        }

        ConversationTopic saved = conversationTopicRepository.save(conversationTopic);
        return ConversationTopicDto
                .builder()
                .id(saved.getId())
                .topic(saved.getTopic())
                .build();
    }

    @Override
    public ConversationTopicDto getConversationTopicById(long topicId) {
        ConversationTopic conversationTopic = conversationTopicRepository.findById(topicId).orElseThrow(
                () -> new NotFoundException("ConversationTopic not found")
        );
        return ConversationTopicDto
                .builder()
                .id(conversationTopic.getId())
                .topic(conversationTopic.getTopic())
                .build();
    }

    @Override
    public List<ConversationTopicDto> getConversationTopics() {
        return conversationTopicRepository.findAll()
                .stream()
                .map(this::conversationTopicToDto)
                .toList();
    }

    private ConversationTopicDto conversationTopicToDto(ConversationTopic conversationTopic) {
        return ConversationTopicDto
                .builder()
                .id(conversationTopic.getId())
                .topic(conversationTopic.getTopic())
                .build();
    }
}
