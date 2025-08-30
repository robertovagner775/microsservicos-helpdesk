package com.roberto.ticket.repositories;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void existsByUsername() {
    }
}