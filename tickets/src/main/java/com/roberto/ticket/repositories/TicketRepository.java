package com.roberto.ticket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roberto.ticket.models.entities.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer>, JpaSpecificationExecutor<Ticket> {

    @Query("SELECT t FROM Ticket t where t.client.id = :idclient AND t.id = :idticket ")
    Optional<Ticket> findByidClientAndIdTicket(@Param("idclient") Integer idclient,
                                              @Param("idticket") Integer idticket);

    @Query("SELECT t FROM Ticket t where t.client.id = :idclient")
    List<Ticket> findByClient(@Param("idclient") Integer idclient);

}
