package com.roberto.ticket.dtos.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.roberto.ticket.dtos.messages.TicketMessageDTO;
import com.roberto.ticket.models.entities.Category;
import org.springframework.web.multipart.MultipartFile;

import com.roberto.ticket.dtos.requests.TicketRequestDTO;
import com.roberto.ticket.dtos.responses.TechnicalDTO;
import com.roberto.ticket.dtos.responses.TicketResponseDTO;
import com.roberto.ticket.models.entities.Client;
import com.roberto.ticket.models.entities.Ticket;
import com.roberto.ticket.models.enums.Priority;

public class TicketMapper {

    public static Ticket toEntity(TicketRequestDTO request, Client client, Category category) {
        return  new Ticket(request.title(), request.description(), client, Priority.valueOf(request.priority()), category);
    }


    public static TicketResponseDTO toResponse(Ticket ticket) {

        List<TechnicalDTO> technicals = ticket.getTechnicals()
                .stream()
                .map(t -> new TechnicalDTO(t.getId(), t.getName()))
                .collect(Collectors.toList());

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getClient().getId(),
                technicals,
                ticket.getStatus().toString(),
                ticket.getDateStart()
        );
  }

    public static TicketMessageDTO toMessage(Ticket ticket) {

        List<TechnicalDTO> technicals = ticket.getTechnicals()
                .stream()
                .map(t -> new TechnicalDTO(t.getId(), t.getName()))
                .collect(Collectors.toList());

        return new TicketMessageDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getClient().getId(),
                technicals,
                ticket.getStatus().toString(),
                ticket.getDateStart(),
                ticket.getCategory().getId()
        );
    }

  	public static TicketRequestDTO toRequest(String title, String description, String priority) {
		return new TicketRequestDTO(title, description, priority, null);
	}


	public static TicketRequestDTO toRequestArquive(String title, String subject, String description, String priority, MultipartFile document, List<MultipartFile> image) {
		return new TicketRequestDTO(title, description, priority, null);
	}
}
