package com.faboda.s3_image_service.web;


import com.faboda.s3_image_service.service.LapengImageService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/s3")
class S3FileController {

  private final LapengImageService lapengImageService;

  public S3FileController(LapengImageService lapengImageService) {
    this.lapengImageService = lapengImageService;
  }

  @GetMapping("/list")
  public ResponseEntity<List<String>> getFileUrls() {
    return ResponseEntity.ok(lapengImageService.getFileUrls());
  }

  @PostMapping("/upload")
  public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    String url = lapengImageService.uploadPhoto(file);
    return ResponseEntity.ok(url);
  }

}
