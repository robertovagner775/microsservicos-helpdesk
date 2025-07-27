package com.roberto.suporteTecnico.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.suporteTecnico.model.Technical;

@Repository
public interface TechnicalRepository extends JpaRepository<Technical, Integer> {
    
}
