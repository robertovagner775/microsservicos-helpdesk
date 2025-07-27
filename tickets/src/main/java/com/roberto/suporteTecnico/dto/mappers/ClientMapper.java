package com.roberto.suporteTecnico.dto.mappers;

import com.roberto.suporteTecnico.dto.requests.ClientRequestDTO;
import com.roberto.suporteTecnico.dto.responses.ClientResponseDTO;
import com.roberto.suporteTecnico.model.Client;

public class ClientMapper {

    public static ClientRequestDTO toClientRequest(String name, String email, String password, String telephone) {
        return new ClientRequestDTO(name, email, password, telephone);
    }

    public static ClientRequestDTO toClientRequestDTO(Client client) {
       return new ClientRequestDTO(client.getName(), client.getUser().getUsername(), client.getUser().getPassword(), client.getTelephone());
    }

    public static ClientResponseDTO toClientResponseDTO(Client client) {
        return new ClientResponseDTO(client.getId(), client.getName(), client.getUser().getUsername(), client.getTelephone());
    }
}
