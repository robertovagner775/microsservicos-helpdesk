package com.roberto.support.report_documents.dtos;

public record FileErrorDTO(String filename, String error) {

    public static FileErrorDTO createError(String filename, String error) {
       return new FileErrorDTO(filename, error);
    }
}
