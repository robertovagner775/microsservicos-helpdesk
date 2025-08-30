package com.roberto.support.storage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.roberto.support.storage.config.AwsS3Client;
import com.roberto.support.storage.config.constants.AwsConstants;
import com.roberto.support.storage.config.constants.FileConstants;
import com.roberto.support.storage.model.FileTicket;
import com.roberto.support.storage.repository.FileTicketRepository;
import com.roberto.support.storage.validation.FileValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketFileDetailServiceTest {

    @Mock
    private FileTicketRepository fileTicketRepository;

    @Mock
    private StorageS3Service storageS3Service;

    @Mock
    private FileValidation fileValidation;

    @Mock
    private AmazonS3 amazonS3;

    @Mock
    FileTicket fileTicket;

    @Mock
    private TicketFileDetailService ticketFileDetailService2;

    @Spy
    @InjectMocks
    private TicketFileDetailService ticketFileDetailService;

    @BeforeEach
    void setUp() {
         MockitoAnnotations.initMocks(this);

    }

    @Test
    void insertFileAws() throws IOException {

        // ARRANGE
        MultipartFile file1 = new MockMultipartFile(
                "file",
                "teste1.txt",
                "text/plain",
                "Conteúdo do arquivo 1".getBytes()
        );

        MultipartFile file2 = new MockMultipartFile(
                "file",
                "teste2.txt",
                "text/plain",
                "Conteúdo do arquivo 2".getBytes()
        );

        List<MultipartFile> arquivos = List.of(file1, file2);
        String keyTest = UUID.randomUUID() + "_" + FileConstants.TICKET_FILE_NAME;
        String keyTest2 = UUID.randomUUID() + "_" + FileConstants.TICKET_FILE_NAME;

        File fileMock = Mockito.mock(File.class);
        File fileMock2 = Mockito.mock(File.class);


        when(ticketFileDetailService.multipartToFile(file1)).thenReturn(fileMock);
        when(ticketFileDetailService.multipartToFile(file2)).thenReturn(fileMock2);
        when(storageS3Service.uploadFile(fileMock, FileConstants.TICKET_FILE_NAME, AwsConstants.BUCKET_TICKET_DETAILS)).thenReturn(keyTest);
        when(storageS3Service.uploadFile(fileMock2, FileConstants.TICKET_FILE_NAME, AwsConstants.BUCKET_TICKET_DETAILS)).thenReturn(keyTest2);

        ticketFileDetailService.insertFileAws(arquivos ,1);

        var captor = ArgumentCaptor.forClass(FileTicket.class);
        verify(fileValidation, times(1)).validate(arquivos);
        verify(fileTicketRepository, times(1)).save(captor.capture());

        Assert.notNull(captor.getValue() , "Error - The File Ticket is Null");
    }
}