package com.roberto.suporteTecnico.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.roberto.suporteTecnico.model.Ticket;
import com.roberto.suporteTecnico.dto.requests.TicketRequestDTO;
import com.roberto.suporteTecnico.dto.responses.TicketResponseDTO;
import com.roberto.suporteTecnico.service.TicketService;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;

@RequestMapping("/client/{id}/tickets")
@RestController
@Tag(name = "Client Tickets")
public class ClienteTicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity createTicket(@PathVariable Integer id, @RequestBody @Valid TicketRequestDTO ticket) {
		Ticket ticketReturn = ticketService.createTicket(ticket, id);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(ticketReturn.getId())
				.toUri();
	    
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
