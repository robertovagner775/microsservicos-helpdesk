package com.roberto.ticket.services;

import com.roberto.ticket.dtos.requests.TechnicalRequestDTO;
import com.roberto.ticket.dtos.responses.TechnicalResponseDTO;
import com.roberto.ticket.exceptions.ConflictEntityException;
import com.roberto.ticket.entities.Speciality;
import com.roberto.ticket.entities.Technical;
import com.roberto.ticket.entities.Users;
import com.roberto.ticket.repositories.SpecialityRepository;
import com.roberto.ticket.repositories.TechnicalRepository;
import com.roberto.ticket.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnicalServiceTest {

    @Mock
    private TechnicalRepository technicalRepository;

    @Mock
    private SpecialityRepository specialityRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private List<Speciality> specialities;


    @Mock
    private TechnicalResponseDTO technicalResponseDTO;


    @Autowired
    @InjectMocks
    private TechnicalService technicalService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        technicalService = new TechnicalService(technicalRepository, specialityRepository, userRepository);
    }

    @Test
    @DisplayName("Create a Technical Case Success")
    void createTechnicalCaseSuccess() {

        TechnicalRequestDTO requestTest = TechnicalRequestDTO.createTechnicalRequestDTO("Matheus Henrique", "matheushenrique@email.com", "123456", "11 40001-00111", Arrays.asList(1, 4, 5));
        Users user = new Users(requestTest.email(), requestTest.password());
        Technical technical1 = new Technical(1 ,requestTest.name(), user, requestTest.telephone(), null);
        when(userRepository.existsByUsername(requestTest.email())).thenReturn(false);
        when(specialityRepository.findAllById(requestTest.specialities())).thenReturn(specialities);

        var usuario = ArgumentCaptor.forClass(Users.class);
        var tecnico = ArgumentCaptor.forClass(Technical.class);

        when(userRepository.save(usuario.capture())).thenReturn(user);
        when(technicalRepository.save(tecnico.capture())).thenReturn(technical1);

        TechnicalResponseDTO technicalResponseCreated = technicalService.createTechnical(requestTest);

        assertNotNull(technicalResponseCreated);
        verify(userRepository, times(1)).save(any());
        verify(technicalRepository, times(1)).save(any());

    }

    @Test
    @DisplayName("Create a Technical Case Failure")
    void createTechnicalCaseFailure() {

        TechnicalRequestDTO requestTest = TechnicalRequestDTO.createTechnicalRequestDTO("Matheus Henrique", "matheushenrique@email.com", "123456", "11 40001-00111", Arrays.asList(1, 4, 5));
        Users user = new Users(requestTest.email(), requestTest.password());
        Technical technical1 = new Technical(1 ,requestTest.name(), user, requestTest.telephone(), null);
        when(userRepository.existsByUsername(requestTest.email())).thenReturn(true);

        var exception = assertThrows(ConflictEntityException.class, () ->  technicalService.createTechnical(requestTest));

        assertTrue(exception.getMessage().contains(requestTest.email()));
        verify(specialityRepository, never()).findAllById(requestTest.specialities());
        verify(userRepository, never()).save(any());
        verify(technicalRepository, never()).save(any());
    }

    @Test
    void listar() {
    }

    @Test
    void assignTicketToTechnical() {


    }
}