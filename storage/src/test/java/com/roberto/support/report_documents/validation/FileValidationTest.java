package com.roberto.support.report_documents.validation;

import com.roberto.support.report_documents.config.constants.ErrorConstants;
import com.roberto.support.report_documents.config.constants.FileConstants;
import com.roberto.support.report_documents.handler.exceptions.FileException;
import com.roberto.support.report_documents.model.enums.Extension;
import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class FileValidationTest {

    @Mock
    private List<MultipartFile> files;

    @Mock
    private List<String> extensionsPermited;

    @Autowired
    @InjectMocks
    private FileValidation fileValidation;

    @Test
    void validate() throws FileException {

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

        fileValidation.validate(arquivos);

        Assertions.assertDoesNotThrow(() ->  fileValidation.validate(arquivos));
    }


    @Test
    void validateErrorExtension() throws FileException {

        MultipartFile file1 = new MockMultipartFile(
                "file",
                "teste1.txt",
                "text/plain",
                "Conteúdo do arquivo 1".getBytes()
        );

        MultipartFile file2 = new MockMultipartFile(
                "file",
                "teste2.txt",
                "image/webp",
                "Conteúdo do arquivo 2".getBytes()
        );

        MultipartFile file3 = new MockMultipartFile(
                "file",
                "teste3.webp",
                "image/webp",
                "Conteúdo do arquivo 2".getBytes()
        );

        List<MultipartFile> arquivos = List.of(file1, file2, file3);



        FileException fileException = assertThrows(FileException.class, () -> fileValidation.validate(arquivos));


        String expectedMessage = ErrorConstants.EXTENSION_INVALID + FilenameUtils.getExtension(file2.getOriginalFilename());

        String expectedMessageErrorContentType = ErrorConstants.CONTENT_TYPE_INVALID + file2.getContentType();
        List<String> expectedMessageErrorList = List.of(ErrorConstants.EXTENSION_INVALID + FilenameUtils.getExtension(file3.getOriginalFilename()), ErrorConstants.CONTENT_TYPE_INVALID + file3.getContentType());

        Object messageErrorReturned = fileException.getFieldsError().get(file2.getOriginalFilename());
        Object listErrorReturned = fileException.getFieldsError().get(file3.getOriginalFilename());

        Assertions.assertEquals(expectedMessageErrorContentType, messageErrorReturned);
        Assertions.assertEquals(expectedMessageErrorList, listErrorReturned);
    }
}