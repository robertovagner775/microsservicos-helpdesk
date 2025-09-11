package com.roberto.ticket.dtos.requests;

import java.io.IOException;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(name = "File")
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
