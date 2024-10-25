package com.lulu.service;

import com.lulu.model.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String storeFile(MultipartFile file, Integer formId) throws IOException;

    FileInfo getFileName(Integer formId);
}
