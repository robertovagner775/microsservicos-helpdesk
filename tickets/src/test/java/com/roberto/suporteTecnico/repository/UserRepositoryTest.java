package com.roberto.suporteTecnico.repository;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Test
    void existsByUsername() {
    }
}