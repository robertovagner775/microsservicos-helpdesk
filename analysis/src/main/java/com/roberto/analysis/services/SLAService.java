package com.roberto.analysis.services;

import com.roberto.analysis.dtos.mappers.SlaMapper;
import com.roberto.analysis.dtos.requests.SlaRequestDTO;
import com.roberto.analysis.dtos.requests.SlaRequestUpdateDTO;
import com.roberto.analysis.entities.Category;
import com.roberto.analysis.entities.SLA;
import com.roberto.analysis.handler.exceptions.NotFoundException;
import com.roberto.analysis.repositories.CategoryRepository;
import com.roberto.analysis.repositories.SlaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SLAService {

    private final SlaRepository slaRepository;

    private final CategoryRepository categoryRepository;

    private final DurationTicketService durationTicketService;

    @Transactional
    public SLA createNewSLA(SlaRequestDTO request) {
        Category category = categoryRepository.findById(request.idCategory()).orElseThrow(
                () -> new NotFoundException(request.idCategory())
        );
        SLA slaCreated = slaRepository.save(SlaMapper.toEntity(request, category));
        category.setSla(slaCreated);
        categoryRepository.save(category);
        return slaCreated;
    }

    public List<SLA> findAllSLAs() {
        return slaRepository.findAll();
    }

    public SLA findById(String uuid) {
        return slaRepository.findById(uuid).orElseThrow(() -> new NotFoundException(uuid));
    }

    public SLA updateSLA(String uuidSLA, SlaRequestUpdateDTO request) {
        SLA sla = this.findById(uuidSLA);
        if(sla != null) {
            sla.setTitle(request.title());
            sla.setDescription(request.description());
            sla.setTimeResponseMins(request.timeResponseMin());
            sla.setTimeResolutionMins(request.timeResolutionMin());
        }
        return slaRepository.save(sla);
    }


}
