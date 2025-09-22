package com.roberto.support.storage.handler;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@Setter
@Getter
public class ErrorResponse {

    private Timestamp timeStamp;
    private String error;
    private Integer status;
    private String message;
    private String path;


    public ErrorResponse(Timestamp timeStamp, String error, String message, String path, Integer status) {
        this.timeStamp = timeStamp;
        this.error = error;
        this.message = message;
        this.path = path;
        this.status = status;
    }

}
