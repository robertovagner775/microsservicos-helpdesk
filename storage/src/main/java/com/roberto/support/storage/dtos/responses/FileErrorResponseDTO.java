package com.roberto.support.storage.dtos.responses;

import java.sql.Timestamp;
import java.util.Map;

public class FileErrorResponseDTO {
    private Timestamp timeStamp;
    private String error;
    private Integer status;
    private Map<String, Object> fields;
    private String path;

    public FileErrorResponseDTO(String path, String error, Timestamp timeStamp, Map<String, Object> fields, Integer status) {
        this.path = path;
        this.error = error;
        this.timeStamp = timeStamp;
        this.fields = fields;
        this.status = status;
    }

    public FileErrorResponseDTO(Timestamp timeStamp, String error, String path, Integer status, Map<String, Object> fields) {
        this.timeStamp = timeStamp;
        this.error = error;
        this.path = path;
        this.status = status;
        this.fields = fields;
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    public void setFields(Map<String, Object> fields) {
        this.fields = fields;
    }

    public Integer  getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}