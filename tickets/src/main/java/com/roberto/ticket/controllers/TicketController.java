package com.roberto.ticket.controllers;


import com.roberto.ticket.dtos.mappers.TicketMapper;
import com.roberto.ticket.dtos.requests.StatusRequestDTO;
import com.roberto.ticket.dtos.responses.TicketResponseDTO;
import com.roberto.ticket.entities.Ticket;
import com.roberto.ticket.entities.enums.Status;
import com.roberto.ticket.producers.TicketProducer;
import com.roberto.ticket.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PatchMapping("/{id}")
    public ResponseEntity updateStatusTicket(@PathVariable Integer id, @RequestBody StatusRequestDTO request) {
        ticketService.updateStatusTicket(id, Status.valueOf(request.status()));
        return ResponseEntity.status(HttpStatus.OK).build();
    };

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAllTickets(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "date-start", required = false) LocalDate dateStart) {

       return ResponseEntity.ok(ticketService.findAllTickets(title, status, dateStart));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> findTicketByID(@PathVariable Integer id) {
        Ticket ticket = ticketService.findByID(id);
        return ResponseEntity.ok(TicketMapper.toResponse(ticket));
    }

}
