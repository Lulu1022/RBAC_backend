package com.lulu.mapper;

import com.lulu.model.entity.FileInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Insert("INSERT INTO fileinfo (file_name, file_size, file_path, form_id) VALUES (#{fileName}, #{fileSize}, #{filePath}, #{formId})")
    void insertFile(FileInfo fileInfo);

    @Select("select * from fileinfo where form_id=#{formId}")
    FileInfo getFileName(Integer formId);
}
