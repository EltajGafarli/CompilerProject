package com.example.sdpproject.controller;

import com.example.sdpproject.entity.User;
import com.example.sdpproject.repository.UserRepository;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/data")
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final UserRepository userRepository;

    @GetMapping(path = "/hello")
//    @PreAuthorize("hasAuthority('USER')")
    @PermitAll
    public ResponseEntity<?> getData(@AuthenticationPrincipal UserDetails userDetails) {
        log.info(
                userRepository.findById(1L).orElseThrow().toString()
        );
//        System.out.println(userDetails);
        return ResponseEntity
                .ok(
                        List.of("Eltaj", "Gafarli")
                );
    }

    @GetMapping(path = "/user")
    public ResponseEntity<User> getUser() {
        return ResponseEntity
                .ok(userRepository.findById(3L).orElseThrow());
    }
}
