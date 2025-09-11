package com.roberto.ticket.services;

import com.roberto.ticket.dtos.mappers.CategoryMapper;
import com.roberto.ticket.dtos.requests.CategoryRequestDTO;
import com.roberto.ticket.models.entities.Category;
import com.roberto.ticket.handler.exceptions.ConflictEntityException;
import com.roberto.ticket.handler.exceptions.NotFoundException;
import com.roberto.ticket.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequestDTO request) {
        Category category = CategoryMapper.toEntity(request);

        if(categoryRepository.existsByTitle(category.getTitle())) {
            throw new ConflictEntityException("the category "+ category.getTitle() +" already exists");
        }
        return categoryRepository.save(category);
    }
    public Category findCategoryById(String uuid) {
        return categoryRepository.findById(uuid).orElseThrow(
                () -> new NotFoundException(uuid)
        );
    }

}
