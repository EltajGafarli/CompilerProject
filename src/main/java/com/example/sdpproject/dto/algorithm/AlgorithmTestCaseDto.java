package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlgorithmTestCaseDto {
    private String testCase;
    private String correctAnswer;
}
