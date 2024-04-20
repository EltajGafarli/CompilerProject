package com.example.sdpproject.service.auth;

import com.example.sdpproject.entity.user.User;

public interface VerificationService {
    Boolean verifyUser(String email, String verificationCode);

    void verificationCodeSending(User user);

    String generateVerificationCode();
}
