package com.roberto.suporteTecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roberto.suporteTecnico.model.Client;

public interface ClientRepository extends JpaRepository<Client, Integer> {

}
