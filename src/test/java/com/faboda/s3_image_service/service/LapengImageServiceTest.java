package com.faboda.s3_image_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class LapengImageServiceTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private MultipartFile file;

    @InjectMocks
    private LapengImageService lapengImageService;

    @BeforeEach
    void setUp() {
        lapengImageService = new LapengImageService(s3Client);
    }

    @Test
    void testUploadPhoto_EmptyFile() {
        when(file.isEmpty()).thenReturn(true);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            lapengImageService.uploadPhoto(file);
        });
        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void testUploadPhoto_IOException() throws IOException {
        when(file.isEmpty()).thenReturn(false);
        when(file.getOriginalFilename()).thenReturn("test.jpg");
        when(file.getBytes()).thenThrow(new IOException("Failed to read file"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            lapengImageService.uploadPhoto(file);
        });
        assertEquals("Error uploading file to S3", exception.getMessage());
    }

    @Test
    void testGetFileUrls_EmptyBucket() {
        ListObjectsV2Response listObjectsResponse = ListObjectsV2Response.builder()
                .contents(List.of())
                .build();

        when(s3Client.listObjectsV2(any(ListObjectsV2Request.class))).thenReturn(listObjectsResponse);

        List<String> urls = lapengImageService.getFileUrls();

        assertTrue(urls.isEmpty());
        verify(s3Client, times(1)).listObjectsV2(any(ListObjectsV2Request.class));
    }
}