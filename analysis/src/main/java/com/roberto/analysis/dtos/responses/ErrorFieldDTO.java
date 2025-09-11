package com.roberto.analysis.dtos.responses;

import org.springframework.validation.FieldError;

public record ErrorFieldDTO(String name, String error) {
    public ErrorFieldDTO(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
