package com.faboda.s3_image_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    long MAX_AGE_SECS = 3600;
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE","*")
        .maxAge(MAX_AGE_SECS);
  }
}