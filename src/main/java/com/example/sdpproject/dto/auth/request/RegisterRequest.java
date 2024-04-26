package com.example.sdpproject.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String firstName;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String lastName;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String userName;

    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String password;
}
