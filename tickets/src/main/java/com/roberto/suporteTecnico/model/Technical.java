package com.roberto.suporteTecnico.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
public class Technical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String telephone;

    @OneToOne
    @JoinColumn(name = "username")
    private Users user;

    @Column(name = "average_satisfaction")
    private Double averageSatisfaction;

    private Boolean available;

    @ManyToMany
    @JoinTable(
            name = "technical_speciality",
            joinColumns = @JoinColumn(name = "technical_id"),
            inverseJoinColumns = @JoinColumn(name = "speciality_id")
    )
    private Set<Speciality> specialities = new HashSet<>();


    @ManyToMany(mappedBy = "technicals")
    private List<Ticket> tickets = new ArrayList<>();

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<Ticket> getChamados() {
        return tickets;
    }

    public void setChamados(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Set<Speciality> getEspecialidades() {
        return specialities;
    }

    public void setEspecialidades(Set<Speciality> specialities) {
        this.specialities = specialities;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    public Double getAverageSatisfaction() {
        return averageSatisfaction;
    }

    public void setAverageSatisfaction(Double averageSatisfaction) {
        this.averageSatisfaction = averageSatisfaction;
    }




    public Technical() {
        
    }

    public Technical(Integer id, String name, Users user, String telephone,
                     Set<Speciality> specialities) {
        this.id = id;
        this.name = name;
        this.available = true;
        this.telephone = telephone;
        this.specialities = specialities;
        this.user = user;
    }
    public Boolean getAvailable() {
        return available;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }
    public Set<Speciality> getSpecialities() {
        return specialities;
    }
    public void addSpeciality(Speciality speciality) {
        getSpecialities().add(speciality);
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }



}