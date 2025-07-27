package com.roberto.suporteTecnico.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record TicketRequestDTO(
		
		@NotBlank
		String title,
		
		@NotBlank
		String description,

		@NotBlank
		@NotNull
		String priority,

		@Size(max = 3)
		List<MultipartFile> files


		) {

	public static TicketRequestDTO createTicketRequest(String title, String description, String priority) {
		return new TicketRequestDTO(title, description, priority, null);
	}


	public static TicketRequestDTO createTicketRequestArquive(String title, String subject,  String description, String priority, MultipartFile document, List<MultipartFile> image) {
		return new TicketRequestDTO(title, description, priority, null);
	}


}
