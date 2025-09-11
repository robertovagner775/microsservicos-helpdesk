package com.roberto.ticket.dtos.requests;

import com.roberto.ticket.models.entities.Client;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Client")
public record ClientRequestDTO(String name, String email, String password, String telephone) {

    public static ClientRequestDTO createClientRequest(String name, String email, String password, String telephone) {
        return new ClientRequestDTO(name, email, password, telephone);
    }

    public ClientRequestDTO(Client client) {
        this(client.getName(), client.getUser().getUsername(), client.getUser().getPassword(), client.getTelephone());
    }
}
