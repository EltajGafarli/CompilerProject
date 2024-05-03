package com.example.sdpproject.dto.algorithm;

import com.example.sdpproject.dto.user.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationDto {
    private Long id;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String conversationName;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String topicName;
    private UserDto userDto;
}
