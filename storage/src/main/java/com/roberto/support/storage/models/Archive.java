package com.roberto.support.storage.models;

import com.roberto.support.storage.dtos.responses.FileDTO;
import jakarta.persistence.*;



@Entity
public class Archive {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String filekey;
    private String filename;
    private String filetype;
    private String bucket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ticket")
    private FileTicket fileTicket;

    public Archive(FileDTO fileDTO) {
        this.filekey = fileDTO.key();
        this.bucket = fileDTO.bucket();
        this.filetype = fileDTO.filetype();
        this.filename = fileDTO.filename();
    }

    public Archive(String key, String filename, FileTicket fileTicket, String bucket, String type) {
        this.filekey = key;
        this.filename = filename;
        this.fileTicket = fileTicket;
        this.bucket = bucket;
        this.filetype = type;
    }
    
    public Archive() {
    	
    }

    public FileTicket getFileTicket() {
        return fileTicket;
    }

    public String getBucket() {
        return bucket;
    }

    public String getFiletype() {
        return filetype;
    }

    public String getFilename() {
        return filename;
    }

    public String getKey() {
        return filekey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFileTicket(FileTicket fileTicket) {
        this.fileTicket = fileTicket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setKey(String key) {
        this.filekey = key;
    }
}
