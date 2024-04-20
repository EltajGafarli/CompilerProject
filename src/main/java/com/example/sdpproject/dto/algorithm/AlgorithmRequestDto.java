package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlgorithmRequestDto {
    private String title;
    private String constraints;
    private String problemStatement;
    private String difficultyLevel;
}
