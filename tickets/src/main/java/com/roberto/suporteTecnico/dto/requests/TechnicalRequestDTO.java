package com.roberto.suporteTecnico.dto.requests;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;


public record TechnicalRequestDTO(
		
		@NotBlank(message = "Nome do técnico e obrigatório")
		String name,
		
		@NotBlank(message = "E-mail e obrigatório")
		@Email
		String email,
		
		@NotBlank
		@Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
		String password,

		@NotBlank
		@NotNull
		String telephone,

		List<Integer> specialities
		) {

	public static TechnicalRequestDTO createTechnicalRequestDTO(String name, String email, String password, String telephone, List<Integer> specialities) {
		return new TechnicalRequestDTO(name, email, password, telephone, specialities);
	}
} 
