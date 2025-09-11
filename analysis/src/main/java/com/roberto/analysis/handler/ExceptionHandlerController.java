package com.roberto.analysis.handler;

import com.roberto.analysis.dtos.responses.ErrorFieldDTO;
import com.roberto.analysis.handler.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound404(NotFoundException notFoundException, HttpServletRequest request) {

        ErrorResponse err = new ErrorResponse(
                Timestamp.from(Instant.now()),
                "Not Found",
                notFoundException.getMessage(),
                request.getRequestURI(),
                404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        var erro = ex.getFieldErrors();
        List<ErrorFieldDTO> list = erro.stream().map(ErrorFieldDTO::new).toList();

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        HttpServletRequest servletRequest = servletWebRequest.getRequest();

        ErrorResponseListFields errorResponse = new ErrorResponseListFields();

        errorResponse.setTimeStamp(Timestamp.from(Instant.now()));
        errorResponse.setStatus(400);
        errorResponse.setMessage("Method Argument Not Valid Exception");
        errorResponse.setPath(servletRequest.getRequestURI());
        errorResponse.setFields(list);

        return ResponseEntity.badRequest().body(errorResponse);
    }
}
