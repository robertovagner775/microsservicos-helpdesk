package com.roberto.support.storage.model;


import com.roberto.support.storage.dtos.responses.FileDTO;

public class Archive {

    private String key;
    private String filename;
    private String type;
    private String bucket;

    public Archive() {
    }

    public Archive(String key, String filename, String type, String bucket) {
        this.key = key;
        this.filename = filename;
        this.type = type;
        this.bucket = bucket;
    }

    public Archive(FileDTO fileDTO) {
        this.key = fileDTO.key();
        this.bucket = fileDTO.bucket();
        this.type = fileDTO.filetype();
        this.filename = fileDTO.filename();
    }

    public Archive(String filename, String type, String bucket) {
        this.filename = filename;
        this.type = type;
        this.bucket = bucket;
    }

    public String getKey() {
        return key;
    }

    public void setId(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
}
