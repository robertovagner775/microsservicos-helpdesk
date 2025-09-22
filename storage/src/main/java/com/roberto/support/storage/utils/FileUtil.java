package com.roberto.support.storage.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUtil {

    public File multipartToFile(MultipartFile multipart) throws IOException {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), multipart.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(multipart.getBytes());
        }
        return path.toFile();
    }
}
