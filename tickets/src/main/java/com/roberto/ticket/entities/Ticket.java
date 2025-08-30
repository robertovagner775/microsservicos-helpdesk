package com.roberto.ticket.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import com.roberto.ticket.entities.enums.Priority;
import com.roberto.ticket.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Ticket {
	
	public Ticket(String title, String description, Client client, Technical technical) {
		this.title = title;
		this.description = description;
		this.client = client;
		this.status = Status.OPEN;
		this.dateStart = LocalDate.now();
        this.addTecnico(technical);
	}

    public Ticket(String title, String description, Client client, Priority priority, Category category) {
        this.title = title;
        this.description = description;
        this.client = client;
        this.status = Status.OPEN;
        this.dateStart = LocalDate.now();
        this.dateEnd = null;
        this.technicals = new ArrayList<Technical>();
        this.priority = priority;
        this.category = category;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false) // FK para Cliente
    private Client client;
    
    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @OneToOne
    private Category category;

    private LocalDate dateStart;

    private LocalDate dateEnd;

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setTechnicals(List<Technical> technicals) {
        this.technicals = technicals;
    }

    @ManyToMany
    @JoinTable(
            name = "ticket_technical",
            joinColumns = @JoinColumn(name = "ticket_id"),
            inverseJoinColumns = @JoinColumn(name = "technical_id")
    )
    private List<Technical> technicals = new ArrayList<>();

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public List<Technical> getTechnicals() {
        return technicals;
    }

    public void addTecnico(Technical technical) {
        this.technicals.add(technical);
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDate getDateStart() {
        return dateStart;
    }
    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }


}
