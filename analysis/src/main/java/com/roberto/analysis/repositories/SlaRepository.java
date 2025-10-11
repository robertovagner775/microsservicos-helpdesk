package com.roberto.analysis.repositories;

import com.roberto.analysis.entities.SLA;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SlaRepository extends JpaRepository<SLA, String> {
	
	@Query("SELECT s FROM DurationTicket dt INNER JOIN Category c ON dt.category.id = c.id  INNER JOIN SLA s  ON c.sla.id = s.id WHERE dt.idTicket = :idticket")
	Optional<SLA> findSLAbyIdTicket(@Param("idticket") Integer idTicket);
}
