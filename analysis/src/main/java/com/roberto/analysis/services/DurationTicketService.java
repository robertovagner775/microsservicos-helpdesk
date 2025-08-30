package com.roberto.analysis.services;

import com.roberto.analysis.dtos.messages.TicketMessageDTO;
import com.roberto.analysis.dtos.mappers.DurationTicketMapper;
import com.roberto.analysis.entities.Category;
import com.roberto.analysis.entities.DurationTicket;
import com.roberto.analysis.repositories.CategoryRepository;
import com.roberto.analysis.repositories.DurationTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DurationTicketService {

    private final DurationTicketRepository durationTicketRepository;

    private final CategoryRepository categoryRepository;

    public DurationTicket save(TicketMessageDTO message) {

        Category category = categoryRepository.findById(message.categoryID()).get();

        DurationTicket ticketDuration = DurationTicketMapper.messageToEntity(message, category);

        return durationTicketRepository.save(ticketDuration);
    }
}
