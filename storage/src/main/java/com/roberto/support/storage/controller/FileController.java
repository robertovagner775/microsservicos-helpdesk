package com.roberto.support.storage.controller;


import com.roberto.support.storage.dtos.responses.TicketDetailsResponseDTO;
import com.roberto.support.storage.service.TicketFileDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("/tickets/{id}/files")
@RestController
public class FileController {

    @Autowired
    private TicketFileDetailService ticketFileDetailService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity addFileTicket(@PathVariable Integer id, @RequestPart("files") List<MultipartFile> files) throws IOException {
        if (files.size() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only upload up to 3 files at a time.");
        }
        ticketFileDetailService.insertFileAws(files, id);
        return ResponseEntity.ok().build();
	}

    @GetMapping
    public ResponseEntity<List<TicketDetailsResponseDTO>> getAllFileDetailsTicket(@PathVariable Integer id) {

       List<TicketDetailsResponseDTO> files = ticketFileDetailService.findAllTicketFilesByID(id);

       return ResponseEntity.ok(files);
    }
    
}
