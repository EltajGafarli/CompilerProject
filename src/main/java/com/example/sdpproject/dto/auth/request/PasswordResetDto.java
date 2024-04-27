package com.example.sdpproject.dto.auth.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PasswordResetDto {
    private String resetToken;
    private String newPassword;
}
