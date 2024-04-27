package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.dto.algorithm.ConversationDto;
import com.example.sdpproject.service.compiler.ConversationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/conversation")
@RequiredArgsConstructor
public class ConversationController {
    private final ConversationService conversationService;

    @PostMapping(path = "/create")
    public ResponseEntity<ConversationDto> addConversation(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ConversationDto conversationDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        conversationService.addConversation(userDetails, conversationDto)
                );
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ConversationDto> getConversationById(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        conversationService.getConversationById(id)
                );
    }

    @GetMapping
    public ResponseEntity<List<ConversationDto>> getConversations() {
        return ResponseEntity
                .ok(
                        conversationService.getConversations()
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteConversation(@PathVariable long id) {
        return ResponseEntity
                .ok(
                        conversationService.deleteConversation(id)
                );
    }
}
