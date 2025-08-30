package com.roberto.ticket.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

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

    @PostMapping
    public ResponseEntity createTechnical(@RequestBody @Valid TechnicalRequestDTO technical, UriComponentsBuilder uriBuilder) {
        TechnicalResponseDTO tec = technicalService.createTechnical(technical);
        var uri = uriBuilder.path("/technicals/{id}").buildAndExpand(tec.id()).toUri();
        
        return ResponseEntity.created(uri).body(tec);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity update(@PathVariable Integer id, @RequestBody TechnicalRequestDTO request) {

        technicalService.updateTechnical(request, id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public Page<TechnicalResponseDTO> listAllTechnicals(Pageable paginacao) {
        return this.technicalService.listar(paginacao);
    }

    @GetMapping("/{id}")
    public ResponseEntity findTechnicalId(@PathVariable Integer id) {
        var tecnico = technicalService.findByTechnical(id);
        return ResponseEntity.ok(tecnico);
    }

}
