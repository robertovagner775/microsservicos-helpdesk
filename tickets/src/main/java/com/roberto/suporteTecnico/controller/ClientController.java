package com.roberto.suporteTecnico.controller;

import com.roberto.suporteTecnico.dto.requests.ClientRequestDTO;
import com.roberto.suporteTecnico.dto.responses.ClientResponseDTO;
import com.roberto.suporteTecnico.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    public ClientService clientService;


    @PostMapping
    public ResponseEntity createClient(@RequestBody @Valid ClientRequestDTO client, UriComponentsBuilder uriBuilder) {
        ClientResponseDTO clientResponse = clientService.createClient(client);

        var uri = uriBuilder.path("/clients/{id}").buildAndExpand(clientResponse.id()).toUri();

        return ResponseEntity.created(uri).body(clientResponse);
    }
}
