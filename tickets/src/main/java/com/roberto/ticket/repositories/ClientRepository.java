package com.roberto.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roberto.ticket.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
