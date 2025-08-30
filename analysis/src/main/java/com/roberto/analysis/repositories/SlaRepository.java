package com.roberto.analysis.repositories;

import com.roberto.analysis.entities.SLA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaRepository extends JpaRepository<SLA, String> {
}
