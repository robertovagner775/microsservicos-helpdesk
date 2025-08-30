package com.roberto.analysis.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DurationTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Integer idTicket;

    private String description;

    private LocalDateTime dateChange;

    private String typeChange;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}
