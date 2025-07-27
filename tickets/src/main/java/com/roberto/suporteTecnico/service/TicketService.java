package com.roberto.suporteTecnico.service;

import com.roberto.suporteTecnico.dto.responses.TicketResponseDTO;
import com.roberto.suporteTecnico.model.enums.Priority;
import com.roberto.suporteTecnico.model.enums.Status;
import com.roberto.suporteTecnico.repository.Specs.TicketSpecs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.roberto.suporteTecnico.exceptions.NotFoundException;
import com.roberto.suporteTecnico.model.Ticket;
import com.roberto.suporteTecnico.model.Client;
import com.roberto.suporteTecnico.model.Technical;
import com.roberto.suporteTecnico.dto.mappers.TicketMapper;
import com.roberto.suporteTecnico.dto.requests.TicketRequestDTO;
import com.roberto.suporteTecnico.repository.TicketRepository;
import com.roberto.suporteTecnico.repository.ClientRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	

	@Autowired
	private  TechnicalService technicalService;


	@Transactional
	public Ticket createTicket(TicketRequestDTO ticketRequest, Integer idClient) {
		
		Client client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException("Recurso não foi encontrado ID: ", idClient));

		Ticket ticket = TicketMapper.toEntity(ticketRequest, client);

		Technical technical = technicalService.assignTicketToTechnical(ticket);

		ticket.addTecnico(technical);

        return ticketRepository.save(ticket);
		
	}

	public Optional<Ticket> findClientByTicket(Integer idClient, Integer idTicket) {
		return  ticketRepository.findByidClientAndIdTicket(idClient, idTicket);
	}

	public void deleteTicket(Integer idClient, Integer idTicket) {

		Optional<Ticket> ticket = ticketRepository.findByidClientAndIdTicket(idClient, idTicket);

		if(ticket.isPresent()) {
			ticket.get().getTechnicals().clear();
			ticketRepository.delete(ticket.get());
		}
	}

	public Ticket findByID(Integer idTicket) {
		return ticketRepository.findById(idTicket).orElseThrow(() -> new NotFoundException("Recurso não encontrado ID: ", idTicket));
	}

	public Ticket updateStatusTicket(Integer idticket, Status status) {
		Ticket ticket = ticketRepository.findById(idticket).orElseThrow(() -> new NotFoundException());
		ticket.setStatus(status);
		return ticketRepository.save(ticket);
	}

	public List<TicketResponseDTO> findAllTickets(String title, String status, LocalDate dateStart) {

		Specification<Ticket> specs = Specification.where(((root, query, cb) -> cb.conjunction()));

		if(title != null) {
			specs = specs.and(TicketSpecs.titleLike(title));
		}
		if (status != null) {
			specs = specs.and(TicketSpecs.statusEqual(status));
		}
		if(dateStart != null) {
			specs = specs.and(TicketSpecs.dateStartEqual(dateStart));
		}

		List<Ticket> tickets = ticketRepository.findAll(specs);

		return tickets.stream().map(TicketResponseDTO::createTicketResponse).toList();
	}

	public String dateToString(LocalDate dateStartTicket) {
		Long days =	ChronoUnit.DAYS.between(dateStartTicket, LocalDate.now());
		Long hours =	ChronoUnit.HOURS.between(dateStartTicket, LocalDate.now());
		Long minutes = ChronoUnit.MINUTES.between(dateStartTicket, LocalDate.now());
		if (days == 0) {
			return String.format("Aberto em " + hours + " e " + minutes + " minutos.");
		}
		return String.format("Aberto em " + days + " dias e " + hours + " horas " + minutes + " minutos");
	}
}
