package com.example.sdpproject.mapper.compiler;

import com.example.sdpproject.dto.algorithm.AlgorithmRequestDto;
import com.example.sdpproject.entity.algorithm.Algorithm;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AlgorithmMapper {
    AlgorithmRequestDto algorithmToDto(Algorithm algorithm);

    Algorithm dtoToAlgorithm(AlgorithmRequestDto algorithmRequestDto);
}
