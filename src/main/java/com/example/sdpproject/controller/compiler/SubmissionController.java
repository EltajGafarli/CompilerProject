package com.example.sdpproject.controller.compiler;

import com.example.sdpproject.dto.algorithm.SubmissionDto;
import com.example.sdpproject.service.compiler.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/submissions")
@RequiredArgsConstructor
public class SubmissionController {
    private final SubmissionService submissionService;

    @GetMapping
    public List<SubmissionDto> getAllSubmissions(@AuthenticationPrincipal UserDetails userDetails) {
        return submissionService.getAllSubmissionsOfUser(userDetails);
    }

    @GetMapping(path = "/{id}")
    public SubmissionDto getSubmissionById(@PathVariable long id) {
        return this.submissionService.getSubmissionById(id);
    }
}
