package com.roberto.analysis.dtos.messages;

import com.roberto.analysis.dtos.responses.TechnicalDTO;

import java.time.LocalDate;
import java.util.List;

public record TicketMessageDTO(Integer id, String title, String description, Integer cliente_id, List<TechnicalDTO> technicals,
                               String status, LocalDate dataStart, String categoryID)  {
}
