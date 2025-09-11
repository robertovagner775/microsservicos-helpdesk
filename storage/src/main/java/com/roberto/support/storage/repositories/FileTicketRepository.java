package com.roberto.support.storage.repositories;

import com.roberto.support.storage.models.FileTicket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileTicketRepository extends MongoRepository<FileTicket, Integer> {

    
}
