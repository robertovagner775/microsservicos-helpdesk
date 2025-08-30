package com.roberto.ticket.repositories.Specs;

import com.roberto.ticket.entities.Ticket;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TicketSpecs {

    public static Specification<Ticket> titleLike(String title){
        return (root, query, cb) -> cb.like(cb.upper(root.get("title")), "%" + title.toUpperCase() + "%");
    }

    public static Specification<Ticket> statusEqual(String status){
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public static Specification<Ticket> dateStartEqual(LocalDate data) {
        return (root, query, cb) -> cb.and(
                cb.equal(cb.function("day", Integer.class, root.get("dateStart")), data.getDayOfMonth()),
                cb.equal(cb.function("month", Integer.class, root.get("dateStart")), data.getMonthValue()),
                cb.equal(cb.function("year", Integer.class, root.get("dateStart")), data.getYear())
        );
    }
}
