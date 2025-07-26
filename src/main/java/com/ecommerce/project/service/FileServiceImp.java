package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImp implements FileService {

    @Override
    public String uploadImagePath(String path, MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();

        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId.concat(originalFilename.substring(originalFilename.lastIndexOf(".")));

        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        Files.copy(file.getInputStream() , Paths.get(filePath));

        return fileName;

    }
}
