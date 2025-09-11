package com.roberto.ticket.dtos.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.roberto.ticket.dtos.requests.TechnicalRequestDTO;
import com.roberto.ticket.dtos.responses.TechnicalResponseDTO;
import com.roberto.ticket.models.entities.Technical;

public class TechnicalMapper {

    public static TechnicalRequestDTO toTechnicalRequestDTO(String name, String email, String password, String telephone, List<Integer> specialities) {
		return new TechnicalRequestDTO(name, email, password, telephone, specialities);
	}

    public static TechnicalResponseDTO toTechnicalResponseDTO(Technical tec) {
        return new TechnicalResponseDTO(
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
