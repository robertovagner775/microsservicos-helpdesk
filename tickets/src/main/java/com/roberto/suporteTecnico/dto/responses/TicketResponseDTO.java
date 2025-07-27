package com.roberto.suporteTecnico.dto.responses;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.roberto.suporteTecnico.model.Ticket;

public record TicketResponseDTO(Integer id, String title, String description, Integer cliente_id, List<TechnicalDTO> technicals,
								String status, LocalDate dataStart

) {

  public static TicketResponseDTO createTicketResponse(Ticket ticket) {

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

}
