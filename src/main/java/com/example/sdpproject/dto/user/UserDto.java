package com.example.sdpproject.dto.user;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
}
