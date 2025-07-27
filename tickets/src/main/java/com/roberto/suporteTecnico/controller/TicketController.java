package com.roberto.suporteTecnico.controller;


import com.roberto.suporteTecnico.dto.requests.StatusRequestDTO;
import com.roberto.suporteTecnico.dto.responses.TicketResponseDTO;
import com.roberto.suporteTecnico.model.Ticket;
import com.roberto.suporteTecnico.model.enums.Status;
import com.roberto.suporteTecnico.service.StorageService;
import com.roberto.suporteTecnico.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private StorageService storageService;  

    @PatchMapping("/{id}")
    public ResponseEntity updateStatusTicket(@PathVariable Integer id, @RequestBody StatusRequestDTO request) {
        ticketService.updateStatusTicket(id, Status.valueOf(request.status()));
        return ResponseEntity.status(HttpStatus.OK).build();
    };

    @GetMapping
    public ResponseEntity<List<TicketResponseDTO>> findAllTickets(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "date-start", required = false) LocalDate dateStart) {

       return ResponseEntity.ok(ticketService.findAllTickets(title, status, dateStart));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity addFileTicket( @RequestPart("files") List<MultipartFile> files, @RequestParam("ticketId") Integer ticketId) throws IOException {

        if (files.size() > 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can only upload up to 3 files at a time.");
        }

        storageService.sendMessageToQueue(files);
        
        return ResponseEntity.ok().build();
	}

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findTicketByID(@PathVariable Integer id) {
        Ticket ticket = ticketService.findByID(id);
        return ResponseEntity.ok(ticket);
    }

}
