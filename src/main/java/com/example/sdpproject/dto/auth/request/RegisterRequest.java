package com.example.sdpproject.dto.auth.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String password;
}
