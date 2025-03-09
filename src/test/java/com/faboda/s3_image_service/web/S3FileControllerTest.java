package com.faboda.s3_image_service.web;

import com.faboda.s3_image_service.service.LapengImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class S3FileControllerTest {

    @Mock
    private LapengImageService lapengImageService;

    @InjectMocks
    private S3FileController s3FileController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFileUrls() {
        List<String> urls = Arrays.asList("url1", "url2");
        when(lapengImageService.getFileUrls()).thenReturn(urls);

        ResponseEntity<List<String>> response = s3FileController.getFileUrls();

        assertEquals(ResponseEntity.ok(urls), response);
        verify(lapengImageService, times(1)).getFileUrls();
    }

    @Test
    void testUploadFile() {
        MultipartFile file = mock(MultipartFile.class);
        String url = "uploadedUrl";
        when(lapengImageService.uploadPhoto(file)).thenReturn(url);

        ResponseEntity<String> response = s3FileController.uploadFile(file);

        assertEquals(ResponseEntity.ok(url), response);
        verify(lapengImageService, times(1)).uploadPhoto(file);
    }
}