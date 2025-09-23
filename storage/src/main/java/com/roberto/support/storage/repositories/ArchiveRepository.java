package com.roberto.support.storage.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roberto.support.storage.models.Archive;

@Repository
public interface ArchiveRepository extends JpaRepository<Archive, String> {
    
}
