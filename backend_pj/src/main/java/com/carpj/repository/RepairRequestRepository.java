package com.carpj.repository;

import com.carpj.model.RepairRequest;
import com.carpj.model.User;
import com.carpj.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRequestRepository extends JpaRepository<RepairRequest, Long> {
    
    List<RepairRequest> findByUser(User user);
    
    List<RepairRequest> findByVehicle(Vehicle vehicle);
    
    List<RepairRequest> findByStatus(RepairRequest.RequestStatus status);
    
    List<RepairRequest> findByUserAndStatus(User user, RepairRequest.RequestStatus status);
    
    List<RepairRequest> findByUrgent(boolean urgent);
} 