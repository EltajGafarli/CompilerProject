package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlgorithmResponse {
    private String output;
    private int statusCode;
    private String memory;
    private String cpuTime;
    private String compilationStatus;
    private String projectKey;
}


