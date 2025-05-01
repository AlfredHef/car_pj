package com.carpj.repository;

import com.carpj.model.MaintenanceStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceStaffRepository extends JpaRepository<MaintenanceStaff, Long> {
    MaintenanceStaff findByUsername(String username);
    List<MaintenanceStaff> findBySpecialty(String specialty);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
} 