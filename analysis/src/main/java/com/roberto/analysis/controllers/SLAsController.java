package com.roberto.analysis.controllers;

import com.roberto.analysis.dtos.mappers.SlaMapper;
import com.roberto.analysis.dtos.requests.SlaRequestDTO;
import com.roberto.analysis.dtos.requests.SlaRequestUpdateDTO;
import com.roberto.analysis.dtos.responses.SlaResponseDTO;
import com.roberto.analysis.entities.SLA;
import com.roberto.analysis.handler.ErrorResponse;
import com.roberto.analysis.handler.ErrorResponseListFields;
import com.roberto.analysis.services.SLAService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Tag(name = "SLAs")
@RequiredArgsConstructor
@RequestMapping(value = "/SLAs")
@RestController
public class SLAsController {

    private final SLAService service;

    @Operation(description = "Create a new SLA")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ErrorResponseListFields.class)))
    })
    @PostMapping
    public ResponseEntity<Void> createSLA(@RequestBody @Valid SlaRequestDTO request) {
        SLA slaCreated = service.createNewSLA(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(slaCreated.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSLA(@PathVariable("id") String id, @RequestBody @Valid SlaRequestUpdateDTO request) {
        SLA slaUpdate = service.updateSLA(id, request);
       return ResponseEntity.ok().build();
    }

    @Operation(description = "Return a SLA by uuid parameter.")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/{id}")
    public ResponseEntity<SlaResponseDTO> findSLA(@PathVariable("id") String id) {
       SlaResponseDTO response = SlaMapper.toResponse(service.findById(id));
       return ResponseEntity.ok(response);
    }

    @Operation(description = "Returns a list of SLAs.")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "204", description = "Success no content")
    })
    @GetMapping
    public ResponseEntity<List<SlaResponseDTO>> findAllSLAs() {
         List<SLA> slas = service.findAllSLAs();
         if(slas.isEmpty()) {
             return ResponseEntity.noContent().build();
         }
         return ResponseEntity.ok(slas.stream().map(SlaMapper::toResponse).toList());
    }
}
