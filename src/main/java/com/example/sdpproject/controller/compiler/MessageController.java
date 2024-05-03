package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.dto.algorithm.MessageDto;
import com.example.sdpproject.service.compiler.MessageService;
import liquibase.license.LicenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping(path = "/{conversationId}")
    public ResponseEntity<MessageDto> addMessage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long conversationId, @RequestBody MessageDto messageDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        messageService.addMessage(userDetails, conversationId, messageDto)
                );
    }

    @PostMapping(path = "/{conversationId}/message/{messageId}")
    public ResponseEntity<MessageDto> replyMessage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long conversationId, @PathVariable long messageId, @RequestBody MessageDto messageDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        messageService.replyMessage(userDetails, conversationId, messageId, messageDto)
                );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteMessage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable long id) {
        return ResponseEntity
                .ok(
                        messageService.deleteMessage(userDetails, id)
                );
    }


    @GetMapping("/{id}")
    public ResponseEntity<MessageDto> getMessageById(@PathVariable long id) {
        return ResponseEntity
                .ok(messageService.getMessageById(id));
    }

    @GetMapping(path = "/conversation/{conversationId}")
    public ResponseEntity<List<MessageDto>> getAllMessagesByConversationId(@PathVariable long conversationId) {
        return ResponseEntity
                .ok(
                        messageService.getMessagesByConversationId(conversationId)
                );
    }


}
