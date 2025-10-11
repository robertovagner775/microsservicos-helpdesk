package com.roberto.analysis.services;

import com.roberto.analysis.dtos.mappers.SlaMapper;
import com.roberto.analysis.dtos.requests.SlaRequestDTO;
import com.roberto.analysis.dtos.requests.SlaRequestUpdateDTO;
import com.roberto.analysis.dtos.responses.MetricResponseDTO;
import com.roberto.analysis.dtos.responses.SlaMetricResponseDTO;
import com.roberto.analysis.entities.Category;
import com.roberto.analysis.entities.DurationTicket;
import com.roberto.analysis.entities.SLA;
import com.roberto.analysis.enums.Status;
import com.roberto.analysis.handler.exceptions.NotFoundException;
import com.roberto.analysis.repositories.CategoryRepository;
import com.roberto.analysis.repositories.DurationTicketRepository;
import com.roberto.analysis.repositories.SlaRepository;
import com.roberto.analysis.utils.DateUtil;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SLAService {

    private final SlaRepository slaRepository;

    private final CategoryRepository categoryRepository;

    private final DurationTicketRepository durationTicketRepository;
    
    private final DateUtil dateUtil;

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
    
    public SlaMetricResponseDTO verifySLATicket(Integer idTicket) {
    
    	SLA sla = slaRepository.findSLAbyIdTicket(idTicket).orElseThrow(() -> new NotFoundException(idTicket.toString()));
    	    	
    	
    	Optional<DurationTicket> durationTicketOpen = durationTicketRepository.findByIdTicketAndTypeChange(idTicket, Status.OPEN.name());
    	Optional<DurationTicket> durationTicketAnalysis = durationTicketRepository.findByIdTicketAndTypeChange(idTicket, Status.IN_ANALYSIS.name());
    	Optional<DurationTicket> durationTicketFinal = durationTicketRepository.findByIdTicketAndTypeChange(idTicket, Status.COMPLETED.name());
    	
    	
    	if(durationTicketOpen.isEmpty()) {
    		throw new NotFoundException(idTicket.toString());
    	}
    	
    	LocalDateTime dateStartOpen = durationTicketOpen.get().getDateChange();
     	LocalDateTime dateStartAnalysis = null;
     	LocalDateTime dateFinal = null;
    	Long elapsedTimeOpenToAnalysis = 0l;
    	Long elapsedTimeOpenToResolution = 0l;
    	
    	Long elapsedTime = dateUtil.calculateWorkingMin(dateStartOpen, LocalDateTime.now());   
    	
    	Boolean slaResolutionViolated = elapsedTime > sla.getTimeResolutionMins();
    	Boolean slaResponseViolated = elapsedTime > sla.getTimeResponseMins();
    	
    	Double usagePercentageResponse = Math.min(((double) elapsedTime  * 100) / sla.getTimeResponseMins(), 100.0);
    	Double usagePercentageResolution = Math.min(((double) elapsedTime  * 100) / sla.getTimeResolutionMins(), 100.0);
    	Long slaResolutionRemainingTime =  sla.getTimeResolutionMins() - elapsedTime;
    	Long slaResponseRemainingTime =  sla.getTimeResponseMins() - elapsedTime;

    	if(durationTicketAnalysis.isPresent())  {
    		dateStartAnalysis = durationTicketAnalysis.get().getDateChange();
    		elapsedTimeOpenToAnalysis = dateUtil.calculateWorkingMin(dateStartOpen, dateStartAnalysis);
    		usagePercentageResponse =  Math.min(((double) elapsedTimeOpenToAnalysis  * 100) / sla.getTimeResponseMins(), 100.0);
    		slaResponseViolated = elapsedTimeOpenToAnalysis > sla.getTimeResponseMins();
    		slaResponseRemainingTime =  sla.getTimeResponseMins() - elapsedTimeOpenToAnalysis;
    	}
    	if(durationTicketFinal.isPresent())  {
    		dateFinal = durationTicketFinal.get().getDateChange();
    		elapsedTimeOpenToResolution = dateUtil.calculateWorkingMin(dateStartOpen, dateFinal);
    		usagePercentageResolution =  Math.min(((double) elapsedTimeOpenToResolution  * 100) / sla.getTimeResolutionMins(), 100.0);
    		slaResolutionViolated = elapsedTimeOpenToResolution > sla.getTimeResolutionMins();
    		slaResolutionRemainingTime =  sla.getTimeResolutionMins() - elapsedTimeOpenToResolution;
    	}  
    	
    	MetricResponseDTO metricResolution = new MetricResponseDTO(elapsedTimeOpenToResolution == 0l ? elapsedTime : elapsedTimeOpenToResolution, slaResolutionRemainingTime, usagePercentageResolution, slaResolutionViolated);
    	MetricResponseDTO metricResponse = new MetricResponseDTO(elapsedTimeOpenToAnalysis == 0l ? elapsedTime : elapsedTimeOpenToAnalysis, slaResponseRemainingTime, usagePercentageResponse, slaResponseViolated);

    	return new SlaMetricResponseDTO(idTicket , SlaMapper.toResponseTwo(sla) , metricResolution, metricResponse, LocalDateTime.now());
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
