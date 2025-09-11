package com.roberto.analysis.dtos.mappers;

import com.roberto.analysis.dtos.requests.SlaRequestDTO;
import com.roberto.analysis.dtos.responses.CategoryDTO;
import com.roberto.analysis.dtos.responses.SlaResponseDTO;
import com.roberto.analysis.entities.Category;
import com.roberto.analysis.entities.SLA;

import java.util.List;
import java.util.Locale;

public class SlaMapper {


    public static SLA toEntity(SlaRequestDTO request, Category category) {

        return new SLA(
                null,
                request.title(),
                request.description(),
                request.timeResponseMin(),
                request.timeResolutionMin(),
                category);
    }

   public static SlaResponseDTO toResponse(SLA sla) {

        List<CategoryDTO> categories = sla.getCategories().stream().map(CategoryMapper::toResponse).toList();

        return new SlaResponseDTO(
                sla.getId(),
                sla.getTitle(),
                sla.getDescription(),
                sla.getTimeResponseMins(),
                sla.getTimeResolutionMins(),
                categories
        );
   }
}
