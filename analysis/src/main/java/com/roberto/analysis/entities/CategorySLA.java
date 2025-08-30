package com.roberto.analysis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class CategorySLA {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String idCategory;

    @OneToOne
    private SLA sla;
}
