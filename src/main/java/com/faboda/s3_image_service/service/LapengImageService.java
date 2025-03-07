package com.faboda.s3_image_service.service;


import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class LapengImageService implements S3Service {

  private final S3Client s3Client;
  @Value("${aws.region}")
  private String region;
  @Value("${lapeng.bucket.name}")
  private String lapengS3Bucket;

  public LapengImageService(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  public String uploadPhoto(MultipartFile file) {
    try {
      String key = "uploads/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
      ByteBuffer buffer = ByteBuffer.wrap(file.getBytes());
      s3Client.putObject(
          PutObjectRequest.builder()
              .bucket(lapengS3Bucket)
              .key(key)
              .contentType(file.getContentType())
              .build(),
          software.amazon.awssdk.core.sync.RequestBody.fromByteBuffer(buffer)
      );

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return null;
  }


  @Override
  public List<String> getFileUrls() {
    ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
        .bucket(lapengS3Bucket)
        .build();

    ListObjectsV2Response listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

    return listObjectsResponse.contents().stream()
        .map(s3Object -> String.format("https://%s.s3.%s.amazonaws.com/%s",
            lapengS3Bucket, region, s3Object.key()))
        .collect(Collectors.toList());
  }
}
