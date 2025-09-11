package com.roberto.support.storage.handler.exceptions;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String MESSAGE_ERROR = "Resource with ID %s not found.";

    private String message;
    private String idEntity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdEntity() {
        return idEntity;
    }

    public void setIdEntity(String idEntity) {
        this.idEntity = idEntity;
    }

    public NotFoundException(String idEntity) {
        this.idEntity = idEntity;
        this.message = String.format(MESSAGE_ERROR, idEntity);
    }



}
