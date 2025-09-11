package com.roberto.ticket.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Schema(name = "Ticket")
public record TicketRequestDTO(

		@NotBlank
		@NotNull
		String title,

		@NotBlank
		@NotNull
		String description,

		@NotBlank
		@NotNull
		String priority,

		@NotBlank
		@NotNull
		String categoryID


		) {

	public static TicketRequestDTO createTicketRequest(String title, String description, String priority) {
		return new TicketRequestDTO(title, description, priority, null);
	}


	public static TicketRequestDTO createTicketRequestArquive(String title, String subject,  String description, String priority, MultipartFile document, List<MultipartFile> image) {
		return new TicketRequestDTO(title, description, priority, null);
	}


}
