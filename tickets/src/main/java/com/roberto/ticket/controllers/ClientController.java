package com.roberto.ticket.controllers;

import com.roberto.ticket.dtos.mappers.ClientMapper;
import com.roberto.ticket.dtos.requests.ClientRequestDTO;
import com.roberto.ticket.dtos.responses.ClientResponseDTO;
import com.roberto.ticket.handler.ErrorResponse;
import com.roberto.ticket.models.entities.Client;
import com.roberto.ticket.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Tag(name = "Clients")
@RestController
@RequestMapping("/clients")
public class ClientController {

    public final ClientService clientService;

    @Operation(description = "Create the new client account")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "Success in operation create a new client"),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Conflict Entity Exception", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity createClient(@RequestBody @Valid ClientRequestDTO client, UriComponentsBuilder uriBuilder) {
        ClientResponseDTO clientResponse = clientService.createClient(client);

        var uri = uriBuilder.path("/clients/{id}").buildAndExpand(clientResponse.id()).toUri();
        return ResponseEntity.created(uri).body(clientResponse);
    }

    @Operation(description = "Find client by id")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success return a client by id"),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientResponseDTO> findClient(@PathVariable Integer id) {
        Client client = clientService.findClientByID(id);
        return ResponseEntity.ok(ClientMapper.toClientResponseDTO(client));
    }


}
