package com.roberto.support.storage.services;

import com.roberto.support.storage.config.constants.FileConstants;
import com.roberto.support.storage.dtos.TicketMessageDTO;
import com.roberto.support.storage.dtos.responses.FileDTO;
import com.roberto.support.storage.config.constants.AwsConstants;
import com.roberto.support.storage.dtos.responses.TicketDetailsResponseDTO;
import com.roberto.support.storage.models.Archive;
import com.roberto.support.storage.models.FileTicket;
import com.roberto.support.storage.repositories.FileTicketRepository;
import com.roberto.support.storage.validation.FileValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RequiredArgsConstructor
@Service
public class TicketFileDetailService  {

    private final FileTicketRepository fileTicketRepository;

    private final StorageS3Service storageS3Service;

    private final FileValidation fileValidation;

    public void insertFileAws(List<MultipartFile> multipartFiles, Integer idTicket) throws IOException {

        List<FileDTO> fileDTOS = new ArrayList<FileDTO>();

        fileValidation.validate(multipartFiles);

        for (MultipartFile multipartFile : multipartFiles) {
            File file = this.multipartToFile(multipartFile);
            String key = storageS3Service.uploadFile(file, FileConstants.TICKET_FILE_NAME, AwsConstants.BUCKET_TICKET_DETAILS);
            FileDTO fileDTO = new FileDTO(key, multipartFile.getOriginalFilename(), multipartFile.getContentType(), AwsConstants.BUCKET_TICKET_DETAILS);
            fileDTOS.add(fileDTO);
            file.delete();
        }
       FileTicket fileTicket = this.saveFile(fileDTOS, idTicket);
        fileTicketRepository.save(fileTicket);
    }

    public FileTicket createFileTicketEmpty(TicketMessageDTO message) {
        FileTicket file = new FileTicket();
        file.setIdTicket(message.id());
        return fileTicketRepository.save(file);
    }

    public List<TicketDetailsResponseDTO> findAllTicketFilesByID(Integer id) {

        FileTicket files = fileTicketRepository.findById(id).get();

        List<TicketDetailsResponseDTO> logFiles = files.getFiles().stream().map(
                a -> new TicketDetailsResponseDTO(
                        a.getFilename(),
                        a.getType(),
                        storageS3Service.generateUrl(a.getBucket(),a.getType(),  a.getKey()).toString()))
                .toList();
        return logFiles;
    }

    public FileTicket saveFile(List<FileDTO> files, Integer idTicket) {
        Optional<FileTicket> fileTicket = fileTicketRepository.findById(idTicket);
        List<Archive> archives = files.stream().map(Archive::new).toList();

        if(fileTicket.isPresent()) {
           fileTicket.get().getFiles().addAll(archives);
           return fileTicket.get();
        }
        return new FileTicket(idTicket, archives);
    }

    public  File multipartToFile(MultipartFile multipart) throws IOException {
        Path path = Paths.get(System.getProperty("java.io.tmpdir"), multipart.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(path)) {
            os.write(multipart.getBytes());
        }
        return path.toFile();
    }
}
