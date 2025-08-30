package com.roberto.analysis.services;

import com.roberto.analysis.entities.Category;
import com.roberto.analysis.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository repository;

    @Transactional
    public Category save(Category category) {
         return  repository.save(category);
    }
}
