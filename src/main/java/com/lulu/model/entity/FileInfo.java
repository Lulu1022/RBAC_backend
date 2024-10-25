package com.lulu.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    private String id;
    private String fileName;
    private Long fileSize;
    private String filePath;
    private Integer formId;
}
