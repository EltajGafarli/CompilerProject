package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.dto.algorithm.ConversationTopicDto;
import com.example.sdpproject.service.compiler.ConversationTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/conversationtopic")
@RequiredArgsConstructor
public class ConversationTopicController {
    private final ConversationTopicService conversationTopicService;

    @PostMapping(path = "/create")
    public ResponseEntity<ConversationTopicDto> addConversationTopic(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ConversationTopicDto conversationTopicDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        conversationTopicService.addTopic(conversationTopicDto)
                );
    }

    @GetMapping
    public ResponseEntity<List<ConversationTopicDto>> getConversationTopics() {
        return ResponseEntity
                .ok(
                        conversationTopicService.getConversationTopics()
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ConversationTopicDto> getConversationTopicById(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        conversationTopicService.getConversationTopicById(id)
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteConversationTopic(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        conversationTopicService.deleteConversationTopic(id)
                );
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<ConversationTopicDto> updateConversationTopic(@PathVariable long id, @RequestBody ConversationTopicDto conversationTopicDto) {
        return ResponseEntity
                .ok(
                        conversationTopicService.updateConversationTopic(id, conversationTopicDto)
                );
    }
}
