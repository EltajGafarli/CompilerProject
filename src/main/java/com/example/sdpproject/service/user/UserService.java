package com.example.sdpproject.service.user;

import com.example.sdpproject.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    UserDto getCurrentUser(UserDetails userDetails);
}
