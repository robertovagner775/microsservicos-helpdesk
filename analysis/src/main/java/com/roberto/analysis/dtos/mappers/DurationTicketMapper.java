package com.roberto.analysis.dtos.mappers;

import com.roberto.analysis.dtos.messages.TicketMessageDTO;
import com.roberto.analysis.entities.Category;
import com.roberto.analysis.entities.DurationTicket;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DurationTicketMapper {

    public static DurationTicket messageToEntity(TicketMessageDTO message, Category category) {

        return new DurationTicket(
                null,
                message.id(),
                message.description(),
                LocalDateTime.now(),
                "TICKET CREATED",
                category
        );
    }
}
