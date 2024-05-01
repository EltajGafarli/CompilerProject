package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubjectResponseDto {
    private long subjectId;
    private String title;
    private String description;
    private int rank;
    private String algorithmTag;
    private List<AlgorithmResponseDto> algorithms;
}
