package com.example.sdpproject.repository.auth;

import com.example.sdpproject.entity.user.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findPasswordResetTokenByResetPasswordToken(String resetPasswordToken);
}
