package com.roberto.suporteTecnico.exceptions;

public class ConflictEntityException extends RuntimeException {

    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ConflictEntityException(String message) {
        super(message);
        this.message = message;
    }

    public ConflictEntityException() {
    }

}
