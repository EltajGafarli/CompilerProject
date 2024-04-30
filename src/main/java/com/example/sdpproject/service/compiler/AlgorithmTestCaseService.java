package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.AlgorithmTestCaseDto;

public interface AlgorithmTestCaseService {
    String addTestCase(long algorithmId, AlgorithmTestCaseDto algorithmTestCaseDto);
}
