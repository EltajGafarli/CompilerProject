package com.example.sdpproject.dto.user;

import com.example.sdpproject.entity.enums.RoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class UserDto {

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

    @Email(message = "Email should valid")
    private String email;

    private List<String> roleName;
}
