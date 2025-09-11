package com.roberto.ticket.services;

import com.roberto.ticket.dtos.responses.TicketResponseDTO;
import com.roberto.ticket.models.entities.Category;
import com.roberto.ticket.models.enums.Status;
import com.roberto.ticket.repositories.Specs.TicketSpecs;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.roberto.ticket.handler.exceptions.NotFoundException;
import com.roberto.ticket.models.entities.Ticket;
import com.roberto.ticket.models.entities.Client;
import com.roberto.ticket.models.entities.Technical;
import com.roberto.ticket.dtos.mappers.TicketMapper;
import com.roberto.ticket.dtos.requests.TicketRequestDTO;
import com.roberto.ticket.repositories.TicketRepository;
import com.roberto.ticket.repositories.ClientRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TicketService {

	private final TicketRepository ticketRepository;
	private final ClientRepository clientRepository;
	private final  TechnicalService technicalService;
	private final CategoryService categoryService;

	@Transactional
	public Ticket createTicket(TicketRequestDTO ticketRequest, Integer idClient) {
		
		Client client = clientRepository.findById(idClient).orElseThrow(() -> new NotFoundException(idClient.toString()));
		Category category = categoryService.findCategoryById(ticketRequest.categoryID());
		Ticket ticket = TicketMapper.toEntity(ticketRequest, client, category);

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
		} else {
			throw new NotFoundException(idTicket.toString());
		}
	}

	public Ticket findByID(Integer idTicket) {
		return ticketRepository.findById(idTicket).orElseThrow(() -> new NotFoundException(idTicket.toString()));
	}

	public void updateStatusTicket(Integer idticket, Status status) {
		Ticket ticket = this.findByID(idticket);
		ticket.setStatus(status);
		ticketRepository.save(ticket);
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
