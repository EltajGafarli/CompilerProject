package com.example.sdpproject.service.compiler.impl;

import com.example.sdpproject.entity.algorithm.Algorithm;
import com.example.sdpproject.repository.compiler.AlgorithmImageRepository;
import com.example.sdpproject.repository.compiler.AlgorithmRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class AlgorithmImageServiceTest {

    @Mock
    private AlgorithmRepository algorithmRepository;
    @Mock
    private AlgorithmImageRepository algorithmImageRepository;
    @InjectMocks
    private AlgorithmImageServiceImpl service;

    @Before
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleFileUpload_ValidFile_ReturnsUrl() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.png");
//        when(file.transferTo(any(Path.class))).thenReturn(null);

        Algorithm algorithm = new Algorithm();
        algorithm.setId(1L);
        when(algorithmRepository.findById(1L)).thenReturn(Optional.of(algorithm));

        String result = service.handleFileUpload(1L, file);
        verify(file, times(1)).transferTo(any(Path.class));
        assertEquals("/files/[UUID]_test.png", result); // Use a regex or specific string that includes the UUID
    }

    @Test
    public void testHandleFileUpload_EmptyFile_ThrowsException() {
        MultipartFile file = mock(MultipartFile.class);
        when(file.isEmpty()).thenReturn(true);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            service.handleFileUpload(1L, file);
        });

        assertEquals("Cannot upload an empty file.", exception.getMessage());
    }
}
