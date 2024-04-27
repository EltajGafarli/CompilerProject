package com.example.sdpproject.service.auth.impl;

import com.example.sdpproject.dto.auth.request.LoginRequest;
import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.email.MailSenderService;
import com.example.sdpproject.entity.enums.RoleEnum;
import com.example.sdpproject.entity.user.Role;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.AlreadyExistException;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.exception.UserNotVerifiedException;
import com.example.sdpproject.mapper.auth.UserMapper;
import com.example.sdpproject.mapper.auth.UserMapperImpl;
import com.example.sdpproject.repository.auth.RoleRepository;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.service.auth.AuthService;
import com.example.sdpproject.service.auth.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackOn = Exception.class)
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final MailSenderService mailSenderService;
    private final VerificationService verificationService;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Value(value = "${admin.email}")
    private String ADMIN_EMAIL;

    @Override
    public String register(RegisterRequest request) {

        userRepository.findUserByEmailOrNameOfUser(request.getEmail(), request.getUserName())
                .ifPresent(
                        data -> {throw new AlreadyExistException("User already exist!");}
                );

        User user = getUser(request);
        user.addRole(
                Role
                        .builder()
                        .role(RoleEnum.USER)
                        .build()
        );
        if (ADMIN_EMAIL.equals(user.getEmail())) {
            user.addRole(
                    Role
                            .builder()
                            .role(RoleEnum.ADMIN)
                            .build()
            );
        }

        userRepository.save(user);
        verificationService.verificationCodeSending(user);
        return "Register";
    }

    @Override
    public UserDto authentication(LoginRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new NotFoundException("User not found!"));
        if (!user.isEnabled()) {
            throw new UserNotVerifiedException("User not verified");
        }
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail().trim(), request.getPassword().trim())
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticate);
        securityContextRepository.saveContext(
                context, httpServletRequest, httpServletResponse);
        log.info(user.getNameOfUser());
        return this
                .getUserDto(user);
    }


    private User getUser(RegisterRequest request) {
        return User.builder()
                .nameOfUser(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .build();
    }


    private UserDto getUserDto(User user) {
        return UserDto
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getNameOfUser())
                .email(user.getEmail())
                .build();
    }


}
