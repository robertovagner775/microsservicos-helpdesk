package com.roberto.suporteTecnico.service;


import com.roberto.suporteTecnico.exceptions.ConflictEntityException;
import com.roberto.suporteTecnico.model.Speciality;
import com.roberto.suporteTecnico.model.Ticket;
import com.roberto.suporteTecnico.model.Users;
import com.roberto.suporteTecnico.repository.SpecialityRepository;
import com.roberto.suporteTecnico.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.rsocket.RSocketProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.roberto.suporteTecnico.exceptions.NotFoundException;
import com.roberto.suporteTecnico.model.Technical;
import com.roberto.suporteTecnico.dto.mappers.TechnicalMapper;
import com.roberto.suporteTecnico.dto.requests.TechnicalRequestDTO;
import com.roberto.suporteTecnico.dto.responses.TechnicalResponseDTO;
import com.roberto.suporteTecnico.repository.TechnicalRepository;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TechnicalService {

    @Autowired
    private TechnicalRepository technicalRepository;

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TechnicalResponseDTO createTechnical(TechnicalRequestDTO technical) {

        if(userRepository.existsByUsername(technical.email()))
            throw new ConflictEntityException("O e-mail: "+ technical.email() +" fornecido já está associado a uma conta existente");

        Set<Speciality> specialities = new HashSet<>();

        if (technical.specialities() != null && !technical.specialities().isEmpty()) {
            List<Speciality> specialitiesRetorno = specialityRepository.findAllById(technical.specialities());
            if(specialitiesRetorno.size() != 0) specialities.addAll(specialitiesRetorno);
        }

        Users user = new Users(technical.email(), technical.password());
        Users userCreate = userRepository.save(user);

        Technical tec = technicalRepository.save(new Technical(null, technical.name(), userCreate, technical.telephone(), specialities));

        if (specialities.isEmpty())
            return TechnicalResponseDTO.createTechnicalNotSpecialities(tec);
        return new TechnicalResponseDTO(tec);
    }

    public Page<TechnicalResponseDTO> listar(Pageable paginacao) {
        return technicalRepository.findAll(paginacao).map(TechnicalResponseDTO::new);
    }

    public void updateTechnical(TechnicalRequestDTO request, Integer id) {

        Technical technical = technicalRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Recurso não foi encontrado ID: ", id)
        );
        List<Speciality> specialities = specialityRepository.findAllById(request.specialities());

        technical.setTelephone(request.telephone());
        technical.setName(request.name());
        technical.getSpecialities().addAll(specialities);

        technicalRepository.save(technical);
    }

    public TechnicalResponseDTO findByTechnical(Integer id) {
        Technical tec = technicalRepository.findById(id).orElseThrow(() -> new NotFoundException("Recurso não foi encontrado ID: ", id));

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
