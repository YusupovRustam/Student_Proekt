package com.company.web;

import com.company.entity.FileStorage;
import com.company.service.FileStorageService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/filestorage")
@Tag(name = "FileStorageResourse", description = "Student photo")
public class FileStorageResourse {
    @Autowired
    FileStorageService fileStorageService;

    @Operation(summary = "Student id si bo'yicha student rasmini olish")
    @GetMapping("photo/{userId}")
    public ResponseEntity findByHashId(@PathVariable Long userId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByUserId(userId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType())).contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(fileStorage.getUploadPath()));

    }

    @Operation(summary = "Student id si bo'yicha student rasmini yuklab olish")
    @GetMapping("/download/{userId}")
    public ResponseEntity download(@PathVariable Long userId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByUserId(userId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName\"" +
                        URLEncoder.encode(fileStorage.getName())).contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(fileStorage.getUploadPath()));

    }


}
