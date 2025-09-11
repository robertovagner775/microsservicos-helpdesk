package com.roberto.analysis.dtos.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(name = "SLA")
public record SlaRequestDTO(

        @NotNull
        @NotBlank
        String title,

        @NotNull
        @NotBlank
        String description,

        @Min(value = 5)
        @Max(value = 240)
        @NotNull
        Integer timeResponseMin,

        @Min(value = 60)
        @Max(value = 1440)
        @NotNull
        Integer timeResolutionMin,

        String idCategory
) {
}
