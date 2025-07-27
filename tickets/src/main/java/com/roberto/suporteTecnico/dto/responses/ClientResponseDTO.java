package com.roberto.suporteTecnico.dto.responses;

import com.roberto.suporteTecnico.model.Client;

public record ClientResponseDTO(Integer id, String name, String email, String telephone) {

    public ClientResponseDTO(Client client) {
        this(client.getId(), client.getName(), client.getUser().getUsername(), client.getTelephone());
    }
}
