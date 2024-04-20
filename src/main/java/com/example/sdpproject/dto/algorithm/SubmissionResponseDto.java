package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubmissionResponseDto {
    private String solutionCode;
    private String cpuTime;
    private String memory;
    private boolean isSuccessful;
    private String programmingLanguage;
    private LocalDateTime createdAt;
}
