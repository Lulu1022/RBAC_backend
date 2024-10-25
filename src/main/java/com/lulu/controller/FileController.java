package com.lulu.controller;

import com.lulu.model.entity.FileInfo;
import com.lulu.model.entity.Result;
import com.lulu.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Slf4j
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping(value = "/upload",consumes ="multipart/form-data")
        public Result uploadFile(@RequestPart("file") MultipartFile file, @RequestParam int formId) throws IOException {
        String fileId = fileService.storeFile(file,formId);
        return Result.success("上傳成功:" + fileId);
    }

    @GetMapping("/{forId}")
    public Result getFileName(@PathVariable Integer forId){
        FileInfo fileInfo = fileService.getFileName(forId);
        return Result.success(fileInfo);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        final Path path = Paths.get(System.getProperty("user.dir") + File.separator + "files" + File.separator + fileName);

        response.setContentType("multipart/form-data");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        final WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());

        final FileChannel fileChannel = new FileInputStream(path.toFile()).getChannel();

        fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
        fileChannel.close();
        writableByteChannel.close();
    }
}
