package com.roberto.ticket.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
