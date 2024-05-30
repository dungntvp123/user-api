package com.microservice.user.controller;

import com.microservice.user.config.SystemConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/images")
@Slf4j
public class ImageController {
    @GetMapping("/{name}")
    public ResponseEntity<?> getImage(@PathVariable("name") String name) {
        try {
            File file = new File(SystemConfig.UPLOAD_FILE_DIRECTORY + "image/" + name);
            if (!file.exists() || !file.isFile()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found");
            }

            String contentType = Files.probeContentType(file.toPath());
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid image file");
            }

            InputStream inputStream = new FileInputStream(file);
            byte[] imageBytes = StreamUtils.copyToByteArray(inputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            log.error("Error while reading the file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while reading the file");
        }
    }
}
