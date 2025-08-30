package com.roberto.ticket.controllers;

import com.roberto.ticket.dtos.mappers.CategoryMapper;
import com.roberto.ticket.dtos.requests.CategoryRequestDTO;
import com.roberto.ticket.entities.Category;
import com.roberto.ticket.producers.CategoryProducer;
import com.roberto.ticket.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService service;

    private final CategoryProducer producer;

    @PostMapping
    public ResponseEntity createCategory(@RequestBody @Valid CategoryRequestDTO request) {
       Category category = service.createCategory(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();

        producer.sendMessageCategory(CategoryMapper.toMessage(category));

       return ResponseEntity.created(location).build();
    }

}
