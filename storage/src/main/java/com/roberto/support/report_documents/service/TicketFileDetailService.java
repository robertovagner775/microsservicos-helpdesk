package com.roberto.support.report_documents.service;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.roberto.support.report_documents.config.constants.FileConstants;
import com.roberto.support.report_documents.dtos.FileDTO;
import com.roberto.support.report_documents.config.AwsS3Client;
import com.roberto.support.report_documents.config.constants.AwsConstants;
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
    private AwsS3Client awsS3Client;

    @Autowired
    private FileValidation fileValidation;

    public void insertFileAws(List<MultipartFile> multipartFiles, Integer idTicket) throws IOException {

        List<FileDTO> fileDTOS = new ArrayList<FileDTO>();

        fileValidation.validate(multipartFiles);

        for (MultipartFile multipartFile : multipartFiles) {
            File file = this.multipartToFile(multipartFile);
            String key = UUID.randomUUID() + "_" + FileConstants.TICKET_FILE_NAME;
            awsS3Client.s3Client().putObject(new PutObjectRequest(AwsConstants.BUCKET_TICKET_DETAILS, key, file));
            FileDTO fileDTO = new FileDTO(key, multipartFile.getOriginalFilename(), multipartFile.getContentType(), AwsConstants.BUCKET_TICKET_DETAILS);
            fileDTOS.add(fileDTO);
            file.delete();
        }
       FileTicket fileTicket = this.saveFile(fileDTOS, idTicket);
        fileTicketRepository.save(fileTicket);
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
