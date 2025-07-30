package com.roberto.support.report_documents.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.roberto.support.report_documents.config.AwsS3Client;
import com.roberto.support.report_documents.config.constants.AwsConstants;
import com.roberto.support.report_documents.config.constants.FileConstants;
import com.roberto.support.report_documents.dtos.FileDTO;
import com.roberto.support.report_documents.model.FileTicket;
import com.roberto.support.report_documents.repository.FileTicketRepository;
import com.roberto.support.report_documents.service.FIleHelper.FileHelperTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StorageServiceTest {


    @Mock
    private FileTicketRepository fileTicketRepository;

    @Mock
    private AwsS3Client awsS3Client;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    FileTicket fileTicket;

    @Spy
    @InjectMocks
    private StorageService storageService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertFileAws() throws IOException {

        // ARRANGE
        MultipartFile file1 = new MockMultipartFile(
                "file",                 // nome do parâmetro
                "teste1.txt",           // nome do arquivo
                "text/plain",           // tipo do conteúdo
                "Conteúdo do arquivo 1".getBytes() // conteúdo
        );

        MultipartFile file2 = new MockMultipartFile(
                "file",
                "teste2.txt",
                "text/plain",
                "Conteúdo do arquivo 2".getBytes()
        );

        List<MultipartFile> arquivos = List.of(file1, file2);
        File arquivoTemporario = File.createTempFile("teste-", ".tmp");
        File arquivoTemporario1 = File.createTempFile("teste-1", ".tmp");

        when(awsS3Client.s3Client()).thenReturn(amazonS3);
        when(storageService.multipartToFile(file1)).thenReturn(arquivoTemporario);
        when(storageService.multipartToFile(file2)).thenReturn(arquivoTemporario1);

        // ACT
        storageService.insertFileAws(arquivos ,1);

        var captor = ArgumentCaptor.forClass(FileTicket.class);
        verify(awsS3Client.s3Client(), times(2)).putObject(any(PutObjectRequest.class));
        verify(fileTicketRepository, times(1)).save(captor.capture());

        Assert.notNull(captor.getValue() , "Error - The File Ticket is Null");

    }

}