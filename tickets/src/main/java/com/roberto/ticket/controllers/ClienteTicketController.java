package com.roberto.ticket.controllers;

import com.roberto.ticket.dtos.mappers.TicketMapper;
import com.roberto.ticket.producers.TicketProducer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.roberto.ticket.entities.Ticket;
import com.roberto.ticket.dtos.requests.TicketRequestDTO;
import com.roberto.ticket.dtos.responses.TicketResponseDTO;
import com.roberto.ticket.services.TicketService;

import jakarta.validation.Valid;

import java.net.URI;

@RequestMapping("/client/{id}/tickets")
@RestController
@RequiredArgsConstructor
@Tag(name = "Client Tickets")
public class ClienteTicketController {

	private final TicketService ticketService;

	private final TicketProducer producer;

	@PostMapping
	public ResponseEntity createTicket(@PathVariable Integer id, @RequestBody @Valid TicketRequestDTO ticket) {
		Ticket ticketReturn = ticketService.createTicket(ticket, id);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(ticketReturn.getId())
				.toUri();

		producer.sendMessageTicketCreated(TicketMapper.toMessage(ticketReturn));

	    return ResponseEntity.created(location).build();
		
	}

	@GetMapping("/{idTicket}")
	public  ResponseEntity<TicketResponseDTO> findByTicketClient(@PathVariable Integer id, @PathVariable Integer idTicket) {

		Ticket ticket = ticketService.findClientByTicket(id, idTicket).get();

		return ResponseEntity.status(200).body(TicketResponseDTO.createTicketResponse(ticket));
	}


	@DeleteMapping("/{idTicket}")
	public ResponseEntity deleteTicketClient(@PathVariable Integer id, @PathVariable Integer idTicket) {

		ticketService.deleteTicket(id, idTicket);

		return ResponseEntity.noContent().build();
	}

    
}
