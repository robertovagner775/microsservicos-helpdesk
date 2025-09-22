package com.roberto.support.storage.repositories;

import com.roberto.support.storage.models.FileTicket;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileTicketRepository extends JpaRepository<FileTicket, String> {

    Optional<FileTicket> findByIdTicket(Integer idTicket);
}
