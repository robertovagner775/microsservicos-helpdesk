package com.roberto.analysis.handler;

import com.roberto.analysis.dtos.responses.ErrorFieldDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ErrorResponseListFields {

    private Timestamp timeStamp;
    private Integer status;
    private String message;
    private String path;
    private List<ErrorFieldDTO> fields = new ArrayList<ErrorFieldDTO>();

    public ErrorResponseListFields(Timestamp timeStamp, Integer status, String message, String path, List<ErrorFieldDTO> fields) {
        this.timeStamp = timeStamp;
        this.status = status;
        this.message = message;
        this.path = path;
        this.fields = fields;
    }

    public ErrorResponseListFields() {
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<ErrorFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<ErrorFieldDTO> fields) {
        this.fields = fields;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
