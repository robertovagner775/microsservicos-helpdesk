package com.roberto.ticket.repositories;

import com.roberto.ticket.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,  String> {

    boolean existsByUsername(String username);
}
