package com.roberto.suporteTecnico.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.roberto.suporteTecnico.dto.requests.TechnicalRequestDTO;
import com.roberto.suporteTecnico.dto.responses.TechnicalResponseDTO;
import com.roberto.suporteTecnico.model.Technical;

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
