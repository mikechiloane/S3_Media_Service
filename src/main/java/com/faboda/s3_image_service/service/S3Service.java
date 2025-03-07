package com.faboda.s3_image_service.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface S3Service {

   String uploadPhoto(MultipartFile file);
   List<String> getFileUrls();
}
