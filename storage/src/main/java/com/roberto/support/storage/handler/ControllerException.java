package com.roberto.support.storage.handler;


import com.roberto.support.storage.dtos.responses.FileErrorResponseDTO;
import com.roberto.support.storage.handler.exceptions.FileException;
import com.roberto.support.storage.handler.exceptions.NotFoundException;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound404(NotFoundException notFoundException, HttpServletRequest request) {
        ErrorResponse err = new ErrorResponse(Timestamp.from(Instant.now()), "Not Found", notFoundException.getMessage(), request.getRequestURI(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

}
