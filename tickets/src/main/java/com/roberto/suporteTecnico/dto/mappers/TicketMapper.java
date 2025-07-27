package com.roberto.suporteTecnico.dto.mappers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.multipart.MultipartFile;

import com.roberto.suporteTecnico.dto.requests.TechnicalRequestDTO;
import com.roberto.suporteTecnico.dto.requests.TicketRequestDTO;
import com.roberto.suporteTecnico.dto.responses.TechnicalDTO;
import com.roberto.suporteTecnico.dto.responses.TechnicalResponseDTO;
import com.roberto.suporteTecnico.dto.responses.TicketResponseDTO;
import com.roberto.suporteTecnico.model.Client;
import com.roberto.suporteTecnico.model.Technical;
import com.roberto.suporteTecnico.model.Ticket;
import com.roberto.suporteTecnico.model.enums.Priority;
import com.roberto.suporteTecnico.model.enums.Status;

public class TicketMapper {

    public static Ticket toEntity(TicketRequestDTO request, Client client) {
        return  new Ticket(request.title(), request.description(), client, Priority.valueOf(request.priority()));
    }


    public static TicketResponseDTO toTicketResponse(Ticket ticket) {

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

  	public static TicketRequestDTO toTicketRequest(String title, String description, String priority) {
		return new TicketRequestDTO(title, description, priority, null);
	}


	public static TicketRequestDTO toTicketRequestArquive(String title, String subject,  String description, String priority, MultipartFile document, List<MultipartFile> image) {
		return new TicketRequestDTO(title, description, priority, null);
	}
}
