package com.roberto.analysis.dtos.responses;

import com.roberto.analysis.entities.Category;

import java.util.List;

public record SlaResponseDTO(String id, String title, String description, Integer timeResponse, Integer timeResolution, List<CategoryDTO> categories) {
}
