package com.example.sdpproject.dto.user;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDto {
    @NotBlank
    @NotNull
    @NotEmpty
    private String fullName;

    @Email
    private String email;

    @Pattern(regexp = "^\\+?([0-9]{1,3})\\s?([- ]?[0-9]{1,4}){2,3}$\n")
    private String phoneNumber;

    @NotBlank
    @NotNull
    @NotEmpty
    private String message;
}
