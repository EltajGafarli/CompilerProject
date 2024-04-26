package com.example.sdpproject.dto.algorithm;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConversationTopicDto {
    Long id;
    private String topic;
}
