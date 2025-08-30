package com.roberto.ticket.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Speciality {

    @Id
    private Long id;
    private String name;
    private String description;

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Technical> getTecnicos() {
        return technicals;
    }

    public void setTecnicos(Set<Technical> technicals) {
        this.technicals = technicals;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Speciality createSpeciality(Long id, String name, String description) {
        return new Speciality(id, name, description, null);
    }



    @ManyToMany(mappedBy = "specialities")
    private Set<Technical> technicals = new HashSet<>();


}
