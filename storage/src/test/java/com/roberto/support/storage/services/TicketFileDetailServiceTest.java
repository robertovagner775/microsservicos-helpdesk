package com.roberto.support.storage.services;


import com.roberto.support.storage.config.constants.AwsConstants;
import com.roberto.support.storage.config.constants.FileConstants;
import com.roberto.support.storage.models.Archive;
import com.roberto.support.storage.models.FileTicket;
import com.roberto.support.storage.repositories.ArchiveRepository;
import com.roberto.support.storage.repositories.FileTicketRepository;
import com.roberto.support.storage.utils.FileUtil;
import com.roberto.support.storage.validation.FileValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketFileDetailServiceTest {

    @Mock
    private FileTicketRepository fileTicketRepository;
    
    @Mock
    private ArchiveRepository archiveRepository;

    @Mock
    private StorageS3Service storageS3Service;

    @Mock
    private FileValidation fileValidation;

    @Mock
    private FileUtil fileUtil;

    @Spy
    @InjectMocks
    private TicketFileDetailService ticketFileDetailService;


    @Test
    void uploadFileToAWSSuccess() throws IOException {

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
        String keyTest = "04f8f59d-4671-4010-bb34-38acae444b8b_" + FileConstants.TICKET_FILE_NAME;
        String keyTest2 = "0969088a-c314-4631-a71f-6352f8c50c87_" + FileConstants.TICKET_FILE_NAME;

        File fileMock = Mockito.mock(File.class);
        File fileMock2 = Mockito.mock(File.class);

        FileTicket file = new FileTicket(1, new ArrayList<Archive>());

        when(fileUtil.multipartToFile(file1)).thenReturn(fileMock);
        when(fileUtil.multipartToFile(file2)).thenReturn(fileMock2);
        when(storageS3Service.uploadFile(fileMock, FileConstants.TICKET_FILE_NAME, AwsConstants.BUCKET_TICKET_DETAILS)).thenReturn(keyTest);
        when(storageS3Service.uploadFile(fileMock2, FileConstants.TICKET_FILE_NAME, AwsConstants.BUCKET_TICKET_DETAILS)).thenReturn(keyTest2);
        when(fileTicketRepository.findByIdTicket(1)).thenReturn(Optional.of(file));

        ticketFileDetailService.uploadNewFIleAWS(arquivos ,1);

        var captor = ArgumentCaptor.forClass(Archive.class);
        verify(fileValidation, times(1)).validate(arquivos);
        verify(archiveRepository, times(2)).save(captor.capture());

        Assert.notNull(captor.getValue(), "Error - The File Ticket is Null");
    }
}