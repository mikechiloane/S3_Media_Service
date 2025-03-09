package com.faboda.s3_image_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class S3ImageServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(S3ImageServiceApplication.class, args);
  }

}
