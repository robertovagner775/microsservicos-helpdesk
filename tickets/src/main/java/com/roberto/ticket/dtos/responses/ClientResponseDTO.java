package com.roberto.ticket.dtos.responses;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Client")
public record ClientResponseDTO(Integer id, String name, String email, String telephone) {

}
