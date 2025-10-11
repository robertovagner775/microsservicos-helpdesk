package com.roberto.analysis.repositories;

import com.roberto.analysis.entities.DurationTicket;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DurationTicketRepository extends JpaRepository<DurationTicket, String> {
	
	List<DurationTicket> findAllByIdTicket(Integer id);
	
	Boolean existsByIdTicketAndTypeChange(Integer idticket, String typeChange);
	
	Optional<DurationTicket> findByIdTicketAndTypeChange(Integer idTicket, String typeChange);
}
