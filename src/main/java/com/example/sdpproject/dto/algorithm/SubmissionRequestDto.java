package com.example.sdpproject.dto.algorithm;

import com.example.sdpproject.entity.enums.ProgrammingLanguages;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmissionRequestDto {
    private String solutionCode;
    private ProgrammingLanguages programmingLanguages;
}
