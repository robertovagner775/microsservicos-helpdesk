package com.roberto.ticket.dtos.responses;

import com.roberto.ticket.models.entities.Technical;

public record TechnicalDTO(Integer id, String name) {

    public TechnicalDTO(Technical technical) {
        this(technical.getId(), technical.getName());
    }
}
