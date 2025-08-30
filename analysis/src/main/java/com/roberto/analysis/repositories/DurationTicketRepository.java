package com.roberto.analysis.repositories;

import com.roberto.analysis.entities.DurationTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DurationTicketRepository extends JpaRepository<DurationTicket, String> {
}
