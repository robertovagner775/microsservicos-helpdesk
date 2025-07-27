package com.roberto.suporteTecnico.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import com.roberto.suporteTecnico.dto.requests.TechnicalRequestDTO;
import com.roberto.suporteTecnico.dto.responses.TechnicalResponseDTO;
import com.roberto.suporteTecnico.service.TechnicalService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/technicals")
@Tag(name = "Technicals")
public class TechnicalController {

    @Autowired
    private TechnicalService technicalService;

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
