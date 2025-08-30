package com.roberto.support.storage.repository;

import com.roberto.support.storage.model.FileTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileTicketRepository extends MongoRepository<FileTicket, Integer> {

    
}
