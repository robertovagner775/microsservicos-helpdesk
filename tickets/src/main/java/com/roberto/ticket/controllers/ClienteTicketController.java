package com.roberto.ticket.controllers;

import com.roberto.ticket.dtos.mappers.TicketMapper;
import com.roberto.ticket.handler.ErrorResponse;
import com.roberto.ticket.producers.TicketProducer;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.roberto.ticket.models.entities.Ticket;
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

	@Operation(description = "Open the new ticket for system")
	@ApiResponses(value =  {
			@ApiResponse(responseCode = "201", description = "Success created"),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
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

	@Operation(description = "returns a ticket by the entered id")
	@ApiResponses(value =  {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@GetMapping("/{idTicket}")
	public  ResponseEntity<TicketResponseDTO> findByTicketClient(@PathVariable Integer id, @PathVariable Integer idTicket) {
		Ticket ticket = ticketService.findClientByTicket(id, idTicket).get();
		return ResponseEntity.status(200).body(TicketResponseDTO.createTicketResponse(ticket));
	}

	@Operation(description = "deleted a ticket")
	@ApiResponses(value =  {
			@ApiResponse(responseCode = "204", description = "Success no content"),
			@ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
	})
	@DeleteMapping("/{idTicket}")
	public ResponseEntity deleteTicketClient(@PathVariable Integer id, @PathVariable Integer idTicket) {
		ticketService.deleteTicket(id, idTicket);
		return ResponseEntity.noContent().build();
	}

    
}
