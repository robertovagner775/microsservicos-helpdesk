package com.roberto.ticket.controllers;

import com.roberto.ticket.handler.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.roberto.ticket.dtos.requests.TechnicalRequestDTO;
import com.roberto.ticket.dtos.responses.TechnicalResponseDTO;
import com.roberto.ticket.services.TechnicalService;

import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/technicals")
@Tag(name = "Technicals")
public class TechnicalController {

    private final TechnicalService technicalService;

    @Operation(description = "create a new technical")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "success in operation the create a new technical"),
            @ApiResponse(responseCode = "404", description = "resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "conflict entity", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity createTechnical(@RequestBody @Valid TechnicalRequestDTO technical, UriComponentsBuilder uriBuilder) {
        TechnicalResponseDTO tec = technicalService.createTechnical(technical);
        var uri = uriBuilder.path("/technicals/{id}").buildAndExpand(tec.id()).toUri();
        
        return ResponseEntity.created(uri).body(tec);
    }

    @Operation(description = "Update a technical")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "204", description = "success operation the update a technical"),
            @ApiResponse(responseCode = "404", description = "resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "conflict entity", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody TechnicalRequestDTO request) {
        technicalService.updateTechnical(request, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(description = "get all technicals")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success no content"),
            @ApiResponse(responseCode = "404", description = "resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public Page<TechnicalResponseDTO> listAllTechnicals(Pageable paginacao) {
        return this.technicalService.listAllTechnicals(paginacao);
    }

    @Operation(description = "return technical by id")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<TechnicalResponseDTO> findTechnicalId(@PathVariable Integer id) {
        TechnicalResponseDTO technical = technicalService.findByTechnical(id);
        return ResponseEntity.ok(technical);
    }
}
