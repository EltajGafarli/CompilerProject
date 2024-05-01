package com.example.sdpproject.service.compiler;

import com.example.sdpproject.dto.algorithm.SubjectRequestDto;
import com.example.sdpproject.dto.algorithm.SubjectResponseDto;

import java.util.List;

public interface SubjectService {
    String createSubject(SubjectRequestDto requestDto);

    String addAlgorithmToSubject(long subjectId, long algorithmId);

    SubjectResponseDto getSubjectById(long subjectId);

    List<SubjectResponseDto> getSubjects();

    String deleteSubject(long subjectId);

    SubjectResponseDto updateSubject(long subjectId, SubjectRequestDto requestDto);
}
