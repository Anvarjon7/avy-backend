package com.example.avyproject.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

    @GetMapping("/images/{imageName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        Resource file = new FileSystemResource("avy-backend/src/main/resources/static/images/" + imageName);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(file);
    }
}
