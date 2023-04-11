package com.lcwd.electroinic.store.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    public String uploadFile(MultipartFile file, String path) throws IOException;
    public InputStream getResourse(String path, String imageName) throws FileNotFoundException;
}
