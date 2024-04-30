package com.example.sdpproject.service.auth;

import com.example.sdpproject.dto.auth.request.LoginRequest;
import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.JwtResponseDto;

public interface AuthService {
    String register(RegisterRequest request);

    JwtResponseDto authentication(LoginRequest request);
}
