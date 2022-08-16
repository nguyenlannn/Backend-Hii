package com.example.backendhii.controllers.basic;

import com.example.backendhii.basess.BaseResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("${BASE_API}/basic")
public class Test {

    @PostMapping
    public ResponseEntity<BaseResponseDto> uploadFile(@RequestParam MultipartFile lan) throws IOException {
        String fileName = lan.getOriginalFilename();
        System.out.println(fileName);
        String uploadDir = "C:/Users/Admin/Desktop/lann";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = lan.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception ignored) {
        }
        return ResponseEntity.ok().body(BaseResponseDto.success("ok"));
    }
}
