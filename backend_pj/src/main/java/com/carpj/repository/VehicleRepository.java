package com.carpj.repository;

import com.carpj.model.User;
import com.carpj.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByUser(User user);
    Vehicle findByLicensePlate(String licensePlate);
    Vehicle findByVin(String vin);
    boolean existsByLicensePlate(String licensePlate);
} 