package com.roberto.analysis.listener;

import com.roberto.analysis.dtos.messages.CategoryMessageDTO;
import com.roberto.analysis.config.constants.RabbitConstants;
import com.roberto.analysis.dtos.mappers.CategoryMapper;
import com.roberto.analysis.entities.Category;
import com.roberto.analysis.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CategoryListener {

    private final CategoryService service;

    @RabbitListener(queues = {RabbitConstants.QUEUE_CREATED_CATEGORY})
    public void receiveMessageCategoryCreated(@Payload CategoryMessageDTO message) {
        Category category = CategoryMapper.messageToEntity(message);
        service.save(category);
    }
}
