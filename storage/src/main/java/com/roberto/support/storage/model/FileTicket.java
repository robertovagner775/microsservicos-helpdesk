package com.roberto.support.storage.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class FileTicket {

    @Id
    private Integer idTicket;
    private List<Archive> files;

    public FileTicket() {
    }

    public FileTicket(Integer idTicket, List<Archive> files) {
        this.idTicket = idTicket;
        this.files = files;
    }

    public FileTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public Integer getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(Integer idTicket) {
        this.idTicket = idTicket;
    }

    public List<Archive> getFiles() {
        return files;
    }

}
