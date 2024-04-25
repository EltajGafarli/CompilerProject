package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageDto {
    Long id;
    private String message;
}
