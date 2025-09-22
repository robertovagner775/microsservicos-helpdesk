package com.roberto.support.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roberto.support.storage.models.Archive;


public interface ArchiveRepository extends JpaRepository<Archive, String> {
    
}
