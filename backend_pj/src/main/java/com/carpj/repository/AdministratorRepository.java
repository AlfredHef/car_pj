package com.carpj.repository;

import com.carpj.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Administrator findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 