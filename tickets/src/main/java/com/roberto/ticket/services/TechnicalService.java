package com.roberto.ticket.services;


import com.roberto.ticket.handler.exceptions.ConflictEntityException;
import com.roberto.ticket.models.entities.Speciality;
import com.roberto.ticket.models.entities.Ticket;
import com.roberto.ticket.models.entities.Users;
import com.roberto.ticket.repositories.SpecialityRepository;
import com.roberto.ticket.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.roberto.ticket.handler.exceptions.NotFoundException;
import com.roberto.ticket.models.entities.Technical;
import com.roberto.ticket.dtos.mappers.TechnicalMapper;
import com.roberto.ticket.dtos.requests.TechnicalRequestDTO;
import com.roberto.ticket.dtos.responses.TechnicalResponseDTO;
import com.roberto.ticket.repositories.TechnicalRepository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TechnicalService {

    private final TechnicalRepository technicalRepository;

    private final SpecialityRepository specialityRepository;

    private final UserRepository userRepository;

    @Transactional
    public TechnicalResponseDTO createTechnical(TechnicalRequestDTO technical) {

        if(userRepository.existsByUsername(technical.email()))
            throw new ConflictEntityException("O e-mail: "+ technical.email() +" fornecido já está associado a uma conta existente");

        Set<Speciality> specialities = new HashSet<>();

        if (technical.specialities() != null && !technical.specialities().isEmpty()) {
            List<Speciality> specialitiesReturned = specialityRepository.findAllById(technical.specialities());
            if(specialitiesReturned.size() != 0) specialities.addAll(specialitiesReturned);
        }

        Users user = new Users(technical.email(), technical.password());
        Users userCreate = userRepository.save(user);

        Technical tec = technicalRepository.save(new Technical(null, technical.name(), userCreate, technical.telephone(), specialities));

        if (specialities.isEmpty())
            return TechnicalResponseDTO.createTechnicalNotSpecialities(tec);
        return new TechnicalResponseDTO(tec);
    }

    public Page<TechnicalResponseDTO> listAllTechnicals(Pageable pageable) {
        return technicalRepository.findAll(pageable).map(TechnicalResponseDTO::new);
    }

    public void updateTechnical(TechnicalRequestDTO request, Integer id) {

        Technical technical = technicalRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id.toString())
        );
        List<Speciality> specialities = specialityRepository.findAllById(request.specialities());

        technical.setTelephone(request.telephone());
        technical.setName(request.name());
        technical.getSpecialities().addAll(specialities);

        technicalRepository.save(technical);
    }

    public TechnicalResponseDTO findByTechnical(Integer id) {
        Technical tec = technicalRepository.findById(id).orElseThrow(
                () -> new NotFoundException(id.toString())
        );
       return TechnicalMapper.toTechnicalResponseDTO(tec);
    }

    public Technical assignTicketToTechnical(Ticket ticket) {

        List<Technical> technicals = technicalRepository.findAll();

        Technical technical = technicals.stream()
                .filter(Technical::getAvailable)
                .min(Comparator.comparing(t -> t.getChamados().size()))
                .orElse(null);

        return technical;

    }

    
}
