package com.roberto.ticket.producers;

import com.roberto.ticket.config.constants.RabbitMQConstants;
import com.roberto.ticket.dtos.messages.CategoryMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CategoryProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessageCategory(CategoryMessageDTO message) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.DIRECT_EXCHANGE_TICKET, RabbitMQConstants.ROUTING_KEY_CATEGORY_CREATED, message);
    }
}
