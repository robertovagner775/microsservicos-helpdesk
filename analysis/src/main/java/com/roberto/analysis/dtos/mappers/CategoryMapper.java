package com.roberto.analysis.dtos.mappers;


import com.roberto.analysis.dtos.messages.CategoryMessageDTO;
import com.roberto.analysis.entities.Category;

public class CategoryMapper {

    public static CategoryMessageDTO toMessage(Category category) {
        return new CategoryMessageDTO(category.getId(), category.getTitle(), category.getDescription());
    }

    public static Category messageToEntity(CategoryMessageDTO message) {
        return new Category(message.id(), message.title(), message.description());
    }
}