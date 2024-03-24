package com.example.sdpproject.controller;

import com.example.sdpproject.entity.User;
import com.example.sdpproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/data")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    @GetMapping(path = "/hello")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> getData() {
        return ResponseEntity
                .ok(
                        List.of("Eltaj", "Gafarli")
                );
    }

    @GetMapping(path = "/user")
    public ResponseEntity<User> getUser() {
        return ResponseEntity
                .ok(userRepository.findById(1L).orElseThrow());
    }
}
