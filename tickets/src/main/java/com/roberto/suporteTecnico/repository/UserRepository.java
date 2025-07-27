package com.roberto.suporteTecnico.repository;

import com.roberto.suporteTecnico.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users,  String> {

    boolean existsByUsername(String username);
}
