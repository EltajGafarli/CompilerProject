package com.example.sdpproject.compiler;

import com.example.sdpproject.dto.algorithm.AlgorithmRequest;
import com.example.sdpproject.dto.algorithm.AlgorithmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "compiler-request-response",
        url = "https://api.jdoodle.com/v1/execute"
)
public interface CompilerApiClient {
    @PostMapping
    AlgorithmResponse sendAlgorithm(@RequestBody AlgorithmRequest algorithmRequest);
}
