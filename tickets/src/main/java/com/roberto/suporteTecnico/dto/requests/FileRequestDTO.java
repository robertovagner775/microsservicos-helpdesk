package com.roberto.suporteTecnico.dto.requests;

import java.io.IOException;
import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public record FileRequestDTO(String filename, String filepath, byte[] filedata, String filetype) implements Serializable {

    public static FileRequestDTO createFileRequest(MultipartFile file) throws IOException  {

        return new FileRequestDTO(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getBytes(),
                file.getContentType()
        );

    } 
}
