package com.roberto.support.report_documents.handler;


import com.roberto.support.report_documents.dtos.ErrorResponseDTO;
import com.roberto.support.report_documents.dtos.FileErrorResponseDTO;
import com.roberto.support.report_documents.handler.exceptions.FileException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;

@ControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(FileException.class)
    public ResponseEntity<FileErrorResponseDTO> extensionInvalid(FileException fileException, HttpServletRequest request) {
        String error = "Upload failure";
        Timestamp timeError = Timestamp.from(Instant.now());
        FileErrorResponseDTO errorResponse = new FileErrorResponseDTO(timeError, error, request.getRequestURI(), 400, fileException.getFieldsError());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
