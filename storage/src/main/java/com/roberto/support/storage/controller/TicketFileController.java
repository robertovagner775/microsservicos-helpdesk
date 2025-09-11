package com.roberto.support.storage.controller;


import com.roberto.support.storage.dtos.responses.TicketDetailsResponseDTO;
import com.roberto.support.storage.services.TicketFileDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "Ticket Files")
@RequestMapping("/tickets/{id}/files")
@RestController
public class TicketFileController {

    @Autowired
    private TicketFileDetailService ticketFileDetailService;

    @Operation(description = "create a new file")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "201", description = "created success")
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity addFileTicket(@PathVariable Integer id, @RequestPart("files") List<MultipartFile> files) throws IOException {
        if (files.size() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only upload up to 3 files at a time.");
        }
        ticketFileDetailService.insertFileAws(files, id);
        return ResponseEntity.ok().build();
	}

    @Operation(description = "")
    @ApiResponses(value =  {
            @ApiResponse(responseCode = "200", description = "success")
    })
    @GetMapping
    public ResponseEntity<List<TicketDetailsResponseDTO>> getAllFileDetailsTicket(@PathVariable Integer id) {
       List<TicketDetailsResponseDTO> files = ticketFileDetailService.findAllTicketFilesByID(id);
       return ResponseEntity.ok(files);
    }
    
}
