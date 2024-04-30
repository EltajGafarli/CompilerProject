package com.example.sdpproject.service.auth;

import com.example.sdpproject.entity.user.User;
import org.springframework.security.core.userdetails.UserDetails;


public interface PasswordResetTokenService {
    String generateResetToken(UserDetails userDetails);

    User getUserByResetToken(String resetToken);

    void deleteResetToken(String resetToken);

    String generateRandomToken();

    String resetPassword(String resetToken, String newPassword);
}
