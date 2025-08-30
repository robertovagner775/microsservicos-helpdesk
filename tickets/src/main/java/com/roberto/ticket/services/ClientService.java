package com.roberto.ticket.services;

import com.roberto.ticket.dtos.mappers.ClientMapper;
import com.roberto.ticket.dtos.requests.ClientRequestDTO;
import com.roberto.ticket.dtos.responses.ClientResponseDTO;
import com.roberto.ticket.exceptions.ConflictEntityException;
import com.roberto.ticket.exceptions.NotFoundException;
import com.roberto.ticket.entities.Client;
import com.roberto.ticket.entities.Users;
import com.roberto.ticket.repositories.ClientRepository;
import com.roberto.ticket.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final UserRepository userRepository;

    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientRequest) {

        if(userRepository.existsByUsername(clientRequest.email()))
            throw new ConflictEntityException("O e-mail fornecido já está associado a uma conta existente");

        Users userCreated = userRepository.save(new Users(clientRequest.email(), clientRequest.password()));

        Client client = new Client(null, clientRequest.name(), userCreated, clientRequest.telephone());

        Client clientCreated = clientRepository.save(client);

        return ClientMapper.toClientResponseDTO(clientCreated);
    }

    public Client findClientByID(Integer id) {

       Client client = clientRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Recurso não foi encontrado ID: ", id.toString()));

       return client;
    }
    
}
