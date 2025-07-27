package com.roberto.support.report_documents.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class FileTicket {

    @Id
    private Integer idTicket;
    private List<Arquive> Arquives;

    public FileTicket() {
    }

    public FileTicket(Integer idTicket, List<Arquive> arquives) {
        this.idTicket = idTicket;
        Arquives = arquives;
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

    public List<Arquive> getFiles() {
        return Arquives;
    }

}
