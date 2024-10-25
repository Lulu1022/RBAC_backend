package com.lulu.service.impl;

import com.lulu.mapper.FileMapper;
import com.lulu.model.entity.FileInfo;
import com.lulu.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileMapper fileMapper;
    @Override
    public String storeFile(MultipartFile file, Integer formId) throws IOException {
        // 儲存檔案到文件系統、資料庫或雲端存儲等位置，並返回檔案ID或路徑
        String fileId = UUID.randomUUID().toString();
        int index = file.getOriginalFilename().lastIndexOf('.');
        String filename = file.getOriginalFilename().substring(0,index);
        String filetype = file.getOriginalFilename().substring(index);
        Long filesize = file.getSize();
        // 儲存到本機
        file.transferTo(new File("C:/SideProject/user-management/files", filename + "_" + fileId + filetype));
        // 儲到資料庫中
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(fileId);
        fileInfo.setFileName(filename);
        fileInfo.setFileSize(file.getSize());
        fileInfo.setFilePath("測試");
        fileInfo.setFormId(formId);
        fileMapper.insertFile(fileInfo); // 插入檔案記錄到資料庫庫
        return filename;
    }

    @Override
    public FileInfo getFileName(Integer formId) {
        return fileMapper.getFileName(formId);
    }
}
