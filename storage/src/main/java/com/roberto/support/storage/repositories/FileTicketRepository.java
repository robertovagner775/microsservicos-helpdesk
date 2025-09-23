package com.roberto.support.storage.repositories;

import com.roberto.support.storage.models.FileTicket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileTicketRepository extends JpaRepository<FileTicket, Integer> {

    Optional<FileTicket> findByIdTicket(Integer idTicket);
}
