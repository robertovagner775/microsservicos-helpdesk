package com.roberto.ticket.dtos.responses;

import com.roberto.ticket.entities.Client;

public record ClientResponseDTO(Integer id, String name, String email, String telephone) {

    public ClientResponseDTO(Client client) {
        this(client.getId(), client.getName(), client.getUser().getUsername(), client.getTelephone());
    }
}
