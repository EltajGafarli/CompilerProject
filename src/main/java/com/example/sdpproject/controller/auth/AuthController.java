package com.example.sdpproject.controller.auth;

import com.example.sdpproject.dto.auth.request.LoginRequest;
import com.example.sdpproject.dto.auth.request.PasswordResetDto;
import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.repository.auth.PasswordResetTokenRepository;
import com.example.sdpproject.service.auth.AuthService;
import com.example.sdpproject.service.auth.PasswordResetTokenService;
import com.example.sdpproject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterRequest request) {
        log.info(request.toString());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        authService.register(request)
                );
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.authentication(loginRequest, request, response));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity
                .ok(
                        userService.getCurrentUser(userDetails)
                );
    }

    @PostMapping(path = "/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetDto passwordResetDto) {
        return ResponseEntity
                .ok(
                        passwordResetTokenService.resetPassword(
                                passwordResetDto.getResetToken(),
                                passwordResetDto.getNewPassword()
                        )
                );
    }

}
