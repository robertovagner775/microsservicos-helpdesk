package com.roberto.support.storage.consumers;

import com.roberto.support.storage.config.constants.RabbitMQConstants;
import com.roberto.support.storage.dtos.TicketMessageDTO;
import com.roberto.support.storage.services.TicketFileDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class StorageTicketConsumer {

    private final TicketFileDetailService service;

    @RabbitListener(queues = RabbitMQConstants.QUEUE_CREATED_TICKET_STORAGE)
    public void getTicketCreated(@Payload TicketMessageDTO message) {
        service.createFileTicketEmpty(message);
    }
}
