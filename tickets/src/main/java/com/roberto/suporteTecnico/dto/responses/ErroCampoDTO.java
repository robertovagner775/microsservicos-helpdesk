package com.roberto.suporteTecnico.dto.responses;

import org.springframework.validation.FieldError;

public record ErroCampoDTO(String field, String message) {
	public ErroCampoDTO(FieldError erro) {
		this(erro.getField(), erro.getDefaultMessage());
	}
}