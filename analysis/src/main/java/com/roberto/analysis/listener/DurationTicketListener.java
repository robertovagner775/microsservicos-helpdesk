package com.roberto.analysis.listener;

import com.roberto.analysis.config.constants.RabbitConstants;
import com.roberto.analysis.dtos.messages.TicketMessageDTO;
import com.roberto.analysis.services.DurationTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DurationTicketListener {

    private final DurationTicketService service;

    @RabbitListener(queues = {RabbitConstants.QUEUE_CREATED_TICKET})
    public void receiveMessageTicketCreated(@Payload TicketMessageDTO message) {
        service.save(message);
    }
}
