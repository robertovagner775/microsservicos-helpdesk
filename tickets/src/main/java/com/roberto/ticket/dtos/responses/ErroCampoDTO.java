package com.roberto.ticket.dtos.responses;

import org.springframework.validation.FieldError;

public record ErroCampoDTO(String field, String message) {
	public ErroCampoDTO(FieldError erro) {
		this(erro.getField(), erro.getDefaultMessage());
	}
}