package com.roberto.ticket.dtos.responses;

import com.roberto.ticket.models.entities.Technical;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.stream.Collectors;

@Schema(name = "Technical")
public record TechnicalResponseDTO(Integer id, String name, String email, String telephone, List<String> specialities, Boolean available) {

    public TechnicalResponseDTO(Technical tec) {
        this(
                tec.getId(),
                tec.getName(),
                tec.getUser().getUsername(),
                tec.getTelephone() ,
                tec.getSpecialities().isEmpty() ? null : tec.getSpecialities().stream().map(e -> e.getName()).collect(Collectors.toList()),
                tec.getAvailable());
    }

    public static TechnicalResponseDTO createTechnicalNotSpecialities(Technical tec) {
        return new TechnicalResponseDTO(tec.getId(), tec.getName(), tec.getUser().getUsername(), tec.getTelephone() , null, tec.getAvailable());

    }
}
