package com.roberto.ticket.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "Category")
public record CategoryRequestDTO(

        @NotNull
        @NotBlank
        @Size(min=3)
        String title,

        @NotNull
        @NotBlank
        @Size(min = 3, max = 150)
        String description) {
}
