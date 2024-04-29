package com.example.sdpproject.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
