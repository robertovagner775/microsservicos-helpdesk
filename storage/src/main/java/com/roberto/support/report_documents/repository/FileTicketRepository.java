package com.roberto.support.report_documents.repository;

import com.roberto.support.report_documents.model.FileTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileTicketRepository extends MongoRepository<FileTicket, Integer> {

    
}
