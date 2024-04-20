package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AlgorithmResponseDto {
    private long id;
    private String title;
    private String constraints;
    private String problemStatement;
    private String difficulty;
    private List<AlgorithmTestCaseDto> testCases;
}
