package com.example.sdpproject.controller.user;

import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


}
