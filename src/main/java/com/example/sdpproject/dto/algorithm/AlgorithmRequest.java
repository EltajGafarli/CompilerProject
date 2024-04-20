package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlgorithmRequest {
    private String clientId;
    private String clientSecret;
    private String script;
    private String language;
    private int versionIndex;
    private String stdin;
}
