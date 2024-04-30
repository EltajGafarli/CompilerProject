package com.example.sdpproject.service.user;

import com.example.sdpproject.dto.auth.request.RegisterRequest;
import com.example.sdpproject.dto.user.UserDto;
import com.example.sdpproject.entity.enums.RoleEnum;

import java.util.List;

public interface AdminService {
    UserDto addUser(RegisterRequest request);

    String deleteUser(String email);

    String addUserRole(long userId, RoleEnum role);

    String deleteUserRole(long userId, RoleEnum roleEnum);

    List<UserDto> getUsers();


}
