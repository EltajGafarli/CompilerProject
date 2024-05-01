package com.example.sdpproject.controller.user;

import com.example.sdpproject.dto.user.ContactDto;
import com.example.sdpproject.service.user.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/contact")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping
    public ResponseEntity<String> contact(@RequestBody ContactDto contactDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(contactService.contact(contactDto));
    }
}
