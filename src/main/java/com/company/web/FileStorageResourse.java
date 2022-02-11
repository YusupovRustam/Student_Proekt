package com.company.web;

import com.company.entity.FileStorage;
import com.company.service.FileStorageService;
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
public class FileStorageResourse {
    @Autowired
    FileStorageService fileStorageService;

    @GetMapping("photo/{userId}")
    public ResponseEntity findByHashId(@PathVariable Long userId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByUserId(userId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName\"" + URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType())).contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(fileStorage.getUploadPath()));

    }

    @GetMapping("/download/{userId}")
    public ResponseEntity download(@PathVariable Long userId) throws MalformedURLException {
        FileStorage fileStorage = fileStorageService.findByUserId(userId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName\"" +
                        URLEncoder.encode(fileStorage.getName())).contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(fileStorage.getUploadPath()));

    }



}
