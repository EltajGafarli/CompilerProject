package com.example.sdpproject.service;

import com.example.sdpproject.dto.auth.request.LoginRequest;
import com.example.sdpproject.dto.auth.request.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    String register(RegisterRequest request);
    String authentication(LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
