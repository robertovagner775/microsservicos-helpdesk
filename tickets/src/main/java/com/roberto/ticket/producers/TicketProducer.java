package com.roberto.ticket.producers;

import com.roberto.ticket.configs.constants.RabbitMQConstants;
import com.roberto.ticket.dtos.messages.TicketMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TicketProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageTicketCreated(TicketMessageDTO message) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.FANOUT_EXCHANGE_TICKET, "", message);
    }
}
