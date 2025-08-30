package com.roberto.ticket.controllers;

import com.roberto.ticket.dtos.mappers.ClientMapper;
import com.roberto.ticket.dtos.requests.ClientRequestDTO;
import com.roberto.ticket.dtos.responses.ClientResponseDTO;
import com.roberto.ticket.entities.Client;
import com.roberto.ticket.producers.TicketProducer;
import com.roberto.ticket.services.ClientService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Tag(name = "Clients")
@RestController
@RequestMapping("/clients")
public class ClientController {

    public final ClientService clientService;

    @PostMapping
    public ResponseEntity createClient(@RequestBody @Valid ClientRequestDTO client, UriComponentsBuilder uriBuilder) {
        ClientResponseDTO clientResponse = clientService.createClient(client);

        var uri = uriBuilder.path("/clients/{id}").buildAndExpand(clientResponse.id()).toUri();
        return ResponseEntity.created(uri).body(clientResponse);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientResponseDTO> findClient(@PathVariable Integer id) {
        Client client = clientService.findClientByID(id);
        return ResponseEntity.ok(ClientMapper.toClientResponseDTO(client));
    }


}
