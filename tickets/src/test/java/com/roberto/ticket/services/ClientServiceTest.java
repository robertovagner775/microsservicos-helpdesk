package com.roberto.ticket.services;

import com.roberto.ticket.dtos.requests.ClientRequestDTO;
import com.roberto.ticket.dtos.responses.ClientResponseDTO;
import com.roberto.ticket.models.entities.Client;
import com.roberto.ticket.models.entities.Users;
import com.roberto.ticket.repositories.ClientRepository;
import com.roberto.ticket.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {


    @Mock
    private ClientRepository clientRepository;

    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);

        clientService = new ClientService(clientRepository, userRepository);
    }

    @Test
    void createClientCaseSuccess() {

        ClientRequestDTO clientRequestDTO = ClientRequestDTO.createClientRequest("Vagner Albert", "vagneralber@email.com","1234", "(11) 90001-5550");


        Users user = new Users(clientRequestDTO.email(), clientRequestDTO.password());
        Client client = Client.createClient(clientRequestDTO, user);

        when(userRepository.existsByUsername(clientRequestDTO.email())).thenReturn(false);

        var userCaptor = ArgumentCaptor.forClass(Users.class);
        var clientCaptor = ArgumentCaptor.forClass(Client.class);

        when(userRepository.save(userCaptor.capture())).thenReturn(user);
        when(clientRepository.save(clientCaptor.capture())).thenReturn(client);

        ClientResponseDTO clientResponseDTO = clientService.createClient(clientRequestDTO);

        assertNotNull(clientResponseDTO);
        verify(userRepository, times(1)).save(any());
        verify(clientRepository, times(1)).save(any());
    }
}