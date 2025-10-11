package com.roberto.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roberto.ticket.dtos.responses.StatusCountDTO;
import com.roberto.ticket.dtos.responses.TicketDashboardResponseDTO;
import com.roberto.ticket.models.entities.Ticket;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {

    @Query("SELECT t FROM Ticket t where t.client.id = :idclient AND t.id = :idticket ")
    Optional<Ticket> findByidClientAndIdTicket(@Param("idclient") Integer idclient,
                                              @Param("idticket") Integer idticket);

    @Query("SELECT t FROM Ticket t where t.client.id = :idclient")
    List<Ticket> findByClient(@Param("idclient") Integer idclient);


    @Query("""
    SELECT new com.roberto.ticket.dtos.responses.TicketDashboardResponseDTO(
        SUM(CASE WHEN t.status = 'OPEN' THEN 1 ELSE 0 END),
        SUM(CASE WHEN t.status = 'IN_ANALYSIS' THEN 1 ELSE 0 END),
        SUM(CASE WHEN t.status = 'IN_PROGRESS' THEN 1 ELSE 0 END),
        SUM(CASE WHEN t.status = 'COMPLETED' THEN 1 ELSE 0 END)
    )
    FROM Ticket t
    WHERE t.dateStart BETWEEN :dateStart AND :dateEnd
    """)
    TicketDashboardResponseDTO findCountStatus(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

}
