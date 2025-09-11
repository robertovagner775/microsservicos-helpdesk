package com.roberto.ticket.controllers;


import com.roberto.ticket.dtos.mappers.TicketMapper;
import com.roberto.ticket.dtos.requests.StatusRequestDTO;
import com.roberto.ticket.dtos.responses.TicketResponseDTO;
import com.roberto.ticket.handler.ErrorResponse;
import com.roberto.ticket.models.entities.Ticket;
import com.roberto.ticket.models.enums.Status;
import com.roberto.ticket.services.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Tickets")
@RequiredArgsConstructor
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Operation(description = "Updates the ticket status in the system, such as IN_ANALYSIS, IN_PROGRESS, COMPLETED")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "conflict in entity", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity updateStatusTicket(@PathVariable Integer id, @RequestBody StatusRequestDTO request) {
        ticketService.updateStatusTicket(id, Status.valueOf(request.status()));
        return ResponseEntity.status(HttpStatus.OK).build();
    };

    @Operation(description = "Search a tickets by title, status and dateStart")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "204", description = "Success no content", content = @Content(schema = @Schema(implementation = Void.class))),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict Entity", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAllTickets(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "date-start", required = false) LocalDate dateStart) {

       return ResponseEntity.ok(ticketService.findAllTickets(title, status, dateStart));
    }

    @Operation(description = "Search ticket for id")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "conflict entity", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDTO> findTicketByID(@PathVariable Integer id) {
        Ticket ticket = ticketService.findByID(id);
        return ResponseEntity.ok(TicketMapper.toResponse(ticket));
    }

}
