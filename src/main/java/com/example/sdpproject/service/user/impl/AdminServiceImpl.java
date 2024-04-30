package com.example.sdpproject.service.user.impl;

import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.entity.enums.RoleEnum;
import com.example.sdpproject.entity.user.Role;
import com.example.sdpproject.entity.user.User;
import com.example.sdpproject.exception.AlreadyExistException;
import com.example.sdpproject.exception.NotFoundException;
import com.example.sdpproject.mapper.auth.UserMapper;
import com.example.sdpproject.repository.auth.RoleRepository;
import com.example.sdpproject.repository.auth.UserRepository;
import com.example.sdpproject.service.auth.VerificationService;
import com.example.sdpproject.service.user.AdminService;
import com.example.sdpproject.service.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = Exception.class)
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final VerificationService verificationService;
    private final RoleRepository roleRepository;

    @Override
    public UserDto addUser(RegisterRequest request) {

        userRepository.findUserByEmailOrNameOfUser(
                request.getEmail(),
                request.getUserName()
        ).ifPresent(
                (data) -> {
                    throw new AlreadyExistException("User already exist!");
                }
        );

        User user = createUser(request);
        user.addRole(
                Role
                        .builder()
                        .role(RoleEnum.USER)
                        .build()
        );
        User savedUser = userRepository.save(user);
        verificationService.verificationCodeSending(user);
        return UserDto
                .builder()
                .userName(savedUser.getUsername())
                .lastName(savedUser.getLastName())
                .firstName(savedUser.getFirstName())
                .email(savedUser.getEmail())
                .roleName(savedUser.getRoles().stream().map(role -> role.getRole().name()).toList())
                .build();
    }

    public String deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found")
        );
        userRepository.delete(user);
        return "User deleted successfully";
    }

    @Override
    public String addUserRole(long userId, RoleEnum roleEnum) {
        Role role = roleRepository.findRoleByRole(roleEnum).orElseThrow(
                () -> new NotFoundException("Role not found")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        user.addRole(role);
        userRepository.save(user);
        return "Role add successfully";
    }

    @Override
    public String deleteUserRole(long userId, RoleEnum roleEnum) {
        Role role = roleRepository.findRoleByRole(roleEnum).orElseThrow(
                () -> new NotFoundException("Role not found")
        );

        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        boolean removed = user.getRoles().removeIf(currentRole -> currentRole.equals(role));

        if (removed) {
            userRepository.save(user);
        }
        return "Role deleted successfully";
    }

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::getUserDto)
                .toList();

    }


    private User createUser(RegisterRequest request) {
        return User
                .builder()
                .nameOfUser(request.getUserName())
                .email(request.getEmail())
                .lastName(request.getLastName())
                .firstName(request.getFirstName())
                .isEnabled(true)
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private UserDto getUserDto(User user) {
        return UserDto
                .builder()
                .firstName(user.getFirstName())
                .email(user.getEmail())
                .userName(user.getUsername())
                .lastName(user.getLastName())
                .roleName(
                        user
                                .getRoles()
                                .stream()
                                .map(role -> role.getRole().name())
                                .toList()
                )
                .build();
    }
}