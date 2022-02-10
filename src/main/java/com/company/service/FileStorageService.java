package com.company.service;

import com.company.entity.FileStorage;
import com.company.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

@Service
public class FileStorageService {

    @Value("${upload.folder}")
    private String upload;

    @Autowired
    FileStorageRepository fileStorageRepository;

    public void save(MultipartFile multipartFile, Long userId) {

        FileStorage fileStorage = new FileStorage();
        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setFileSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setUserId(userId);

        LocalDate localDate = LocalDate.now();
        String uploadPath = String.format("%s/image/%d/%d/%d", this.upload, localDate.getYear(),
                localDate.getMonthValue(), localDate.getDayOfMonth());
        fileStorage.setUploadPath(uploadPath + "/" + multipartFile.getOriginalFilename());

        FileStorage fileStorage1 = findByUserId(userId);
        if (fileStorage1 != null) {
            fileStorage.setId(fileStorage1.getId());
        }

        fileStorageRepository.save(fileStorage);

        File folder = new File(uploadPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        folder = folder.getAbsoluteFile();
        File file = new File(String.format("%s/%s", folder, multipartFile.getOriginalFilename()));

        try {
            file.createNewFile();
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public FileStorage findByUserId(Long userId) {
        return fileStorageRepository.findByUserId(userId);
    }

    public void delete(Long userId) {
        FileStorage fileStorage = fileStorageRepository.findByUserId(userId);
        File file = new File(fileStorage.getUploadPath());
        if (file.delete())
            fileStorageRepository.deleteById(fileStorage.getId());
    }


}

