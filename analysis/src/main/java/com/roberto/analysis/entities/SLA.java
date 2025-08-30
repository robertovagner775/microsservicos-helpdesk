package com.roberto.analysis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class SLA {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "TIME_RESPONSE_MIN", nullable = false)
    private Integer timeResponseMins;

    @Column(name = "TIME_RESOLUTION_MIN", nullable = false)
    private Integer timeResolutionMins;

    @OneToMany(mappedBy = "sla")
    private List<Category> categories = new ArrayList<>();
}
