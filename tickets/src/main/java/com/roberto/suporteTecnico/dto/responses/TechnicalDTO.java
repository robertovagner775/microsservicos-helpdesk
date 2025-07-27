package com.roberto.suporteTecnico.dto.responses;

import com.roberto.suporteTecnico.model.Technical;

public record TechnicalDTO(Integer id, String name) {

    public TechnicalDTO(Technical technical) {
        this(technical.getId(), technical.getName());
    }
}
