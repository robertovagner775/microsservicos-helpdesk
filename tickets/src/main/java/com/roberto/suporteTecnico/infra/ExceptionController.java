package com.roberto.suporteTecnico.infra;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.roberto.suporteTecnico.exceptions.ConflictEntityException;
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

import com.roberto.suporteTecnico.exceptions.NotFoundException;
import com.amazonaws.Response;
import com.roberto.suporteTecnico.dto.responses.ErroCampoDTO;

@RestControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler  {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> notFound404(NotFoundException notFoundException, HttpServletRequest request) {


		if(notFoundException.getMessage() != null && notFoundException.getIdEntity() != null) {
			ErrorResponse err = new ErrorResponse(
					Timestamp.from(Instant.now()),
					"Not Found",
					notFoundException.getMessage() + "" + notFoundException.getIdEntity(),
					request.getRequestURI(),
					404);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
		}
		ErrorResponse err = new ErrorResponse(Timestamp.from(Instant.now()), "Not Found", notFoundException.getMessage(), request.getRequestURI(), 404);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(ConflictEntityException.class)
	public ResponseEntity<ErrorResponse> conflict409(ConflictEntityException conflictEntityException, HttpServletRequest request) {
		ErrorResponse err = new ErrorResponse(Timestamp.from(Instant.now()), "Conflict Entity Exception" ,conflictEntityException.getMessage(), request.getRequestURI(), 409);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(err);
	}


	@ExceptionHandler(IOException.class) 
	public ResponseEntity<ErrorResponse> ioException(IOException ioException, HttpServletRequest request) {
		ErrorResponse err = new ErrorResponse(Timestamp.from(Instant.now()), "IO Exception", ioException.getMessage(), request.getRequestURI(), 400);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		var erro = ex.getFieldErrors();
		List<ErroCampoDTO> list = erro.stream().map(ErroCampoDTO::new).toList();

		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		HttpServletRequest servletRequest = servletWebRequest.getRequest();

		Map<String, Object> erroRetorno = new LinkedHashMap<>();
		erroRetorno.put("timestamp", Timestamp.from(Instant.now()));
		erroRetorno.put("status", 400);
		erroRetorno.put("message", "Method Argument Not Valid Exception");
		erroRetorno.put("path", servletRequest.getRequestURI());
		erroRetorno.put("errors", list);
		
		return ResponseEntity.badRequest().body(erroRetorno);
	}



}
