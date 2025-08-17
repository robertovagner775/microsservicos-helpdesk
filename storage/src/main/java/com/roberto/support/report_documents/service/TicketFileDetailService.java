package com.roberto.support.report_documents.service;

import com.roberto.support.report_documents.config.constants.FileConstants;
import com.roberto.support.report_documents.dtos.FileDTO;
import com.roberto.support.report_documents.config.constants.AwsConstants;
import com.roberto.support.report_documents.dtos.TicketDetailsResponseDTO;
import com.roberto.support.report_documents.model.Archive;
import com.roberto.support.report_documents.model.FileTicket;
import com.roberto.support.report_documents.repository.FileTicketRepository;
import com.roberto.support.report_documents.validation.FileValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Service
public class TicketFileDetailService {

    @Autowired
    private FileTicketRepository fileTicketRepository;

    @Autowired
    private StorageS3Service storageS3Service;

    @Autowired
    private FileValidation fileValidation;

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
