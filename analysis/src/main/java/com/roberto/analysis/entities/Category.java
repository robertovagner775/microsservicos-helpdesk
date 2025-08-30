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
public class Category {

    @Id
    @Column(columnDefinition = "CHAR(36)")
    private String id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_SLA")
    private SLA sla;

    @OneToMany(mappedBy = "category")
    private List<DurationTicket> durationTicketList = new ArrayList<DurationTicket>();

    public Category(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.durationTicketList = new ArrayList<DurationTicket>();
    }

}