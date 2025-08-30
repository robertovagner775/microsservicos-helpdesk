package com.roberto.ticket.repositories;

import com.roberto.ticket.dtos.requests.ClientRequestDTO;
import com.roberto.ticket.dtos.requests.TicketRequestDTO;
import com.roberto.ticket.entities.Client;
import com.roberto.ticket.entities.Ticket;
import com.roberto.ticket.entities.Users;
import com.roberto.ticket.entities.enums.Priority;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TicketRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void findByidClientAndIdTicket() {

        ClientRequestDTO clientRequest = ClientRequestDTO.createClientRequest("Igor Lup", "igor@email.com", "teste123", "(11) 2002-5002");
        TicketRequestDTO ticketRequest = TicketRequestDTO.createTicketRequest("Problems in computer", "turn on of computer the end of energy", "HIGH");

        Ticket ticket = this.createTicket(clientRequest, ticketRequest);

        Optional<Ticket> ticketResult = ticketRepository.findByidClientAndIdTicket(ticket.getClient().getId(), ticket.getId());

        assertThat(ticketResult.isPresent()).isTrue();
    }

    private Ticket createTicket(ClientRequestDTO client, TicketRequestDTO ticketRequest) {

        Users user = new Users(client.email(), client.password());

        this.entityManager.persist(user);

        Client clientCreated = Client.createClient(client, user);

        this.entityManager.persist(clientCreated);

        Ticket ticket = new Ticket(ticketRequest.title(), ticketRequest.description(), clientCreated, Priority.valueOf(ticketRequest.priority()));

        this.entityManager.persist(ticket);

        return ticket;
    }
}