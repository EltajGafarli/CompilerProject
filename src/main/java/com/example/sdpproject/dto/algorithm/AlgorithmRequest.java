package com.example.sdpproject.dto.algorithm;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlgorithmRequest {
    private String clientId;
    private String clientSecret;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String script;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String language;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private int versionIndex;

    @NotEmpty(message = "Field should'nt empty")
    @NotBlank(message = "Field should'nt blank")
    @NotNull(message = "Field should'nt null")
    private String stdin;
}
