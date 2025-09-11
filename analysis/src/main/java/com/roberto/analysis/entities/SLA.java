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

    public SLA(String id, String title, String description, Integer responseMin, Integer resolutionMin, Category category) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.timeResponseMins = responseMin;
        this.timeResolutionMins = resolutionMin;
        this.categories.add(category);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String title;

    private String description;

    @Column(name = "TIME_RESPONSE_MIN", nullable = false)
    private Integer timeResponseMins;

    @Column(name = "TIME_RESOLUTION_MIN", nullable = false)
    private Integer timeResolutionMins;

    @OneToMany(mappedBy = "sla")
    private List<Category> categories = new ArrayList<Category>();
}
