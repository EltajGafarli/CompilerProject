package com.example.sdpproject.controller;

import com.example.sdpproject.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/auth/verification")
@RequiredArgsConstructor
public class VerificationController {
    private final VerificationService verificationService;

    @PostMapping(path = "/verify")
    public ResponseEntity<String> verify(@RequestParam String email, @RequestParam String verificationCode) {
        boolean isVerified = verificationService.verifyUser(email, verificationCode);
        if(isVerified) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body("User verified successfully");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Verification failed. Invalid email or verification code.");
    }
}