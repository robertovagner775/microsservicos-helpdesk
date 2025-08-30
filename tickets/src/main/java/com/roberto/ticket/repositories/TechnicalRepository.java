package com.roberto.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.ticket.entities.Technical;

@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
    
}
