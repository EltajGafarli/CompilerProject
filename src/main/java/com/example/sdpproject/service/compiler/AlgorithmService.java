package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.AlgorithmRequestDto;
import com.example.sdpproject.dto.algorithm.AlgorithmResponseDto;

import java.util.List;

public interface AlgorithmService {
    String addAlgorithm(AlgorithmRequestDto algorithmRequestDto);

    AlgorithmResponseDto getAlgorithm(long id);

    List<AlgorithmResponseDto> getAllAlgorithms();

    String deleteAlgorithm(long id);

    AlgorithmResponseDto updateAlgorithm(long id, AlgorithmRequestDto algorithmRequestDto);

    List<AlgorithmResponseDto> getAlgorithmsByAlgorithmTag(String tag);
}
