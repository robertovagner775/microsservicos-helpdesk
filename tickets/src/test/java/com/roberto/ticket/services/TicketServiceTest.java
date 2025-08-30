package com.roberto.ticket.services;

import com.roberto.ticket.dtos.requests.TicketRequestDTO;
import com.roberto.ticket.entities.Client;
import com.roberto.ticket.entities.Technical;
import com.roberto.ticket.entities.Ticket;
import com.roberto.ticket.repositories.ClientRepository;
import com.roberto.ticket.repositories.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    
    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private TechnicalService technicalService;

    @Autowired
    @InjectMocks
    private TicketService ticketService;

    @Mock
    private Client client;

    @Mock
    private Ticket ticket;

    @Mock
    private Technical technical;


    private TicketRequestDTO request;

    @Captor
    private ArgumentCaptor<Technical> technicalCaptor;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        ticketService = new TicketService(ticketRepository, clientRepository, technicalService);
    }

    @DisplayName("Ticket Creation: Execution Flow Test")
    @Test
    void createTicketExecutionFlowTest() {

        this.request = TicketRequestDTO.createTicketRequest("Quebrei o computador da empresa", "durante o meu espediente eu quebrei o computador da empresa acidentalmente", "HIGH");

        when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        when(technicalService.assignTicketToTechnical(any(Ticket.class))).thenReturn(technical);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ticket result;
        result = ticketService.createTicket(request, 1);

        assertNotNull(result);
        verify(clientRepository).findById(1);
        verify(technicalService).assignTicketToTechnical(any(Ticket.class));
        verify(ticketRepository).save(any(Ticket.class));


    }

    @Test
    void deleteTicket() {
    }

}