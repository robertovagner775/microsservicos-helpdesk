package com.roberto.ticket.controllers;

import com.roberto.ticket.dtos.mappers.CategoryMapper;
import com.roberto.ticket.dtos.requests.CategoryRequestDTO;
import com.roberto.ticket.handler.ErrorResponse;
import com.roberto.ticket.models.entities.Category;
import com.roberto.ticket.producers.CategoryProducer;
import com.roberto.ticket.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.ws.rs.PUT;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Tag(name = "Categories")
@RequiredArgsConstructor
@RequestMapping("/categories")
@RestController
public class CategoryController {

    private final CategoryService service;

    private final CategoryProducer producer;

    @Operation(description = "create a new category")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "success created"),
            @ApiResponse(responseCode = "409", description = "Conflict Entity Exception", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))

    })
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
