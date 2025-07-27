package com.roberto.suporteTecnico.service;

import com.roberto.suporteTecnico.dto.mappers.ClientMapper;
import com.roberto.suporteTecnico.dto.requests.ClientRequestDTO;
import com.roberto.suporteTecnico.dto.responses.ClientResponseDTO;
import com.roberto.suporteTecnico.exceptions.ConflictEntityException;
import com.roberto.suporteTecnico.model.Client;
import com.roberto.suporteTecnico.model.Users;
import com.roberto.suporteTecnico.repository.ClientRepository;
import com.roberto.suporteTecnico.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ClientResponseDTO createClient(ClientRequestDTO clientRequest) {

        if(userRepository.existsByUsername(clientRequest.email()))
            throw new ConflictEntityException("O e-mail fornecido já está associado a uma conta existente");

        Users userCreated = userRepository.save(new Users(clientRequest.email(), clientRequest.password()));

        Client client = new Client(null, clientRequest.name(), userCreated, clientRequest.telephone());

        Client clientCreated = clientRepository.save(client);

        return ClientMapper.toClientResponseDTO(clientCreated);
    }
    
}
