package com.example.sdpproject.controller.auth;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.sdpproject.dto.auth.request.LoginRequest;
import com.example.sdpproject.dto.auth.request.PasswordResetDto;
import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.service.auth.AuthService;
import com.example.sdpproject.service.auth.PasswordResetTokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

class AuthControllerTest {
    private MockMvc mockMvc;
    private AuthService authService;
    private PasswordResetTokenService passwordResetTokenService;

    @BeforeEach
    public void setup() {
        authService = mock(AuthService.class);
        passwordResetTokenService = mock(PasswordResetTokenService.class);
        AuthController authController = new AuthController(authService, passwordResetTokenService);
        mockMvc = standaloneSetup(authController).build();
    }

    @Test
    void testRegister() throws Exception {
        when(authService.register(any())).thenReturn("User registered successfully");
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(RegisterRequest.builder().build())))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully"));
    }

    @Test
    void testLogin() throws Exception {
        when(authService.authentication(any(), any(), any())).thenReturn(UserDto.builder().build());
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(LoginRequest.builder().build())))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser
    void testResetPassword() throws Exception {
        when(passwordResetTokenService.resetPassword(anyString(), anyString())).thenReturn("Password reset successfully");
        mockMvc.perform(post("/api/auth/reset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(PasswordResetDto.builder().build())))
                .andExpect(status().isOk())
                .andExpect(content().string("Password reset successfully"));
    }

    @Test
    @WithMockUser
    void testResetRequest() throws Exception {
        when(passwordResetTokenService.generateResetToken(any(UserDetails.class))).thenReturn("Reset token generated");
        mockMvc.perform(get("/api/auth/reset_request"))
                .andExpect(status().isOk())
                .andExpect(content().string("Reset token generated"));
    }
}
