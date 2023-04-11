package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.exceptions.BadApiRequestException;
import com.lcwd.electroinic.store.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    public String uploadFile(MultipartFile file, String path) throws IOException {
        String orignalFileName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        String extension = orignalFileName.substring(orignalFileName.lastIndexOf("."));
        String fileNameWithExtension = fileName + extension;
        String fullPathWithFileName = path + fileNameWithExtension;
        if (extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg")) {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();

            }
            Files.copy(file.getInputStream(), Paths.get(fullPathWithFileName));


        } else {
throw new BadApiRequestException("This Extension is not allowed : "+extension);
        }
        return fileNameWithExtension;
    }

    public InputStream getResourse(String path, String imageName) throws FileNotFoundException {
        String fullPath=path+imageName;
InputStream inputStream=new FileInputStream(fullPath);

return inputStream;
        }

}
