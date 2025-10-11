package com.roberto.analysis.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roberto.analysis.dtos.responses.SlaMetricResponseDTO;
import com.roberto.analysis.services.SLAService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RequestMapping("/tickets/{id}/sla-metrics")
@RestController
@RequiredArgsConstructor
@Tag(name = "Tickets Metrics SLAs")
public class TicketSLAController {

	
	private final SLAService service;
	
	@GetMapping
	public ResponseEntity<SlaMetricResponseDTO> verifySLA(@PathVariable("id") Integer idTicket) {
		SlaMetricResponseDTO metrics = service.verifySLATicket(idTicket);
		
		return ResponseEntity.ok(metrics);
	}
}
