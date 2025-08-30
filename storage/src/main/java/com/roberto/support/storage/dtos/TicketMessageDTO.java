package com.roberto.support.storage.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record TicketMessageDTO(Integer id, String title, String description, Integer cliente_id, List<TechnicalDTO> technicals,
                               String status, LocalDate dataStart, String categoryID) implements Serializable {
}
