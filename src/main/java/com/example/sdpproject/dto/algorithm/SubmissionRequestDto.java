package com.example.sdpproject.dto.algorithm;

import com.example.sdpproject.entity.enums.ProgrammingLanguages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionRequestDto {

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String solutionCode;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private ProgrammingLanguages programmingLanguages;
}
