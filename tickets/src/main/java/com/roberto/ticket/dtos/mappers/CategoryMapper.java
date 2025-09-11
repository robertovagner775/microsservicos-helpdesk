package com.roberto.ticket.dtos.mappers;

import com.roberto.ticket.dtos.messages.CategoryMessageDTO;
import com.roberto.ticket.dtos.requests.CategoryRequestDTO;
import com.roberto.ticket.models.entities.Category;

public class CategoryMapper {

    public static Category toEntity(CategoryRequestDTO request) {
        return new Category(null, request.title(), request.description());
    }

    public static CategoryMessageDTO toMessage(Category category) {
        return new CategoryMessageDTO(category.getId(), category.getTitle(), category.getDescription());
    }
}
