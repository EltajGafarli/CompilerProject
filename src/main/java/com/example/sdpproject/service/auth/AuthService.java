package com.example.sdpproject.service.auth;

import com.example.sdpproject.dto.auth.request.LoginRequest;
import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    String register(RegisterRequest request);

    UserDto authentication(LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
