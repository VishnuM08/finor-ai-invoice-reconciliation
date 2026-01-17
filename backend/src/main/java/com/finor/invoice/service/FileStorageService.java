package com.finor.invoice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    public String save(MultipartFile file) throws Exception {
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String safeName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        String filePath = uploadDir + File.separator + safeName;

        Path path = Paths.get(filePath);
        Files.write(path, file.getBytes());

        return filePath;
    }
}
