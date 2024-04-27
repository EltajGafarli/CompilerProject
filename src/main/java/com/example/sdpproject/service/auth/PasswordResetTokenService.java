package com.example.sdpproject.service.auth;

import com.example.sdpproject.entity.user.User;


public interface PasswordResetTokenService {
    String generateResetToken(User user);

    User getUserByResetToken(String resetToken);

    void deleteResetToken(String resetToken);

    String generateRandomToken();
    String resetPassword(String resetToken, String newPassword);
}
