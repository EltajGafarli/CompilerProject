package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionDto {
    private long id;
    private String solutionCode;
    private String cpuTime;
    private String memory;
    private boolean isSuccessful;
    private String programmingLanguages;
}
