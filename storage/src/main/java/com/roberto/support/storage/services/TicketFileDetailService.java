package com.roberto.support.storage.services;

import com.roberto.support.storage.config.constants.FileConstants;
import com.roberto.support.storage.dtos.TicketMessageDTO;
import com.roberto.support.storage.config.constants.AwsConstants;
import com.roberto.support.storage.dtos.responses.TicketDetailsResponseDTO;
import com.roberto.support.storage.handler.exceptions.NotFoundException;
import com.roberto.support.storage.models.Archive;
import com.roberto.support.storage.models.FileTicket;
import com.roberto.support.storage.repositories.ArchiveRepository;
import com.roberto.support.storage.repositories.FileTicketRepository;
import com.roberto.support.storage.utils.FileUtil;
import com.roberto.support.storage.validation.FileValidation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
public class TicketFileDetailService  {

    private final FileTicketRepository fileTicketRepository;
    private final ArchiveRepository archiveRepository;
    private final StorageS3Service storageS3Service;
    private final FileValidation fileValidation;
    private final FileUtil fileUtil;

    @Transactional
    public void uploadNewFIleAWS(List<MultipartFile> multipartFiles, Integer idTicket) throws IOException {
        Optional<FileTicket> fileTicket = fileTicketRepository.findByIdTicket(idTicket);
        if(fileTicket.isEmpty()) 
            throw new NotFoundException(idTicket.toString()); 

        fileValidation.validate(multipartFiles);
        for (MultipartFile multipartFile : multipartFiles) {
            File file = fileUtil.multipartToFile(multipartFile);
            String key = storageS3Service.uploadFile(file, FileConstants.TICKET_FILE_NAME, AwsConstants.BUCKET_TICKET_DETAILS);
            Archive archive = new Archive(key, multipartFile.getOriginalFilename() , fileTicket.get() , AwsConstants.BUCKET_TICKET_DETAILS, multipartFile.getContentType());
            archiveRepository.save(archive);
        }
     
    }

    public void createFileTicketEmpty(TicketMessageDTO message) {
        FileTicket file = new FileTicket();
        file.setIdTicket(message.id());
        fileTicketRepository.save(file);
    }

    public List<TicketDetailsResponseDTO> findAllTicketFilesByID(Integer id) {

        Optional<FileTicket> files = fileTicketRepository.findByIdTicket(id);

        if(files.isEmpty()) throw new NotFoundException(id.toString());

        return files.get().getFiles().stream().map(
                a -> new TicketDetailsResponseDTO(
                		a.getId(),
                        a.getFilename(),
                        a.getFiletype(),
                        storageS3Service.generateUrl(a.getBucket(),a.getFiletype(),  a.getKey()).toString()))
                .toList();
    }

    public void deleteObjectAWS(Integer id, String idfile) {
        Archive archive = archiveRepository.findById(idfile).orElseThrow(() -> new NotFoundException(idfile));
        storageS3Service.removeFile(archive.getKey() , archive.getBucket());
        archiveRepository.delete(archive);
    }
}
