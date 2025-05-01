package com.carpj.repository;

import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;
import com.carpj.model.Vehicle;
import com.carpj.model.WorkOrder;
import com.carpj.model.WorkOrder.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
    List<WorkOrder> findByUser(User user);
    List<WorkOrder> findByVehicle(Vehicle vehicle);
    List<WorkOrder> findByStaff(MaintenanceStaff staff);
    List<WorkOrder> findByStatus(OrderStatus status);
    List<WorkOrder> findByUserAndStatus(User user, OrderStatus status);
} 