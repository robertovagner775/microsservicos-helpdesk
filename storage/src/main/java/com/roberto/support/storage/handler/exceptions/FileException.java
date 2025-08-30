package com.roberto.support.storage.handler.exceptions;

import java.io.IOException;
import java.util.Map;

public class FileException extends IOException {

    private Map<String, Object> fieldsError;

    public FileException(String message, Map<String, Object> fields) {
        super(message);
        this.fieldsError = fields;
    }

    public FileException(String message) {
        super(message);
    }

    public Map<String, Object> getFieldsError() {
        return fieldsError;
    }

    public void setFieldsError(Map<String, Object> fieldsError) {
        this.fieldsError = fieldsError;
    }
}
