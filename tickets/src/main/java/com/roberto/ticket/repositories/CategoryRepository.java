package com.roberto.ticket.repositories;


import com.roberto.ticket.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

    Boolean existsByTitle(String title);
}
