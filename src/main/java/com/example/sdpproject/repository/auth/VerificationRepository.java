package com.example.sdpproject.repository.auth;

import com.example.sdpproject.entity.user.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationRepository extends JpaRepository<Verification, Long> {
    Optional<Verification> findByVerificationCode(String verificationCode);
}
