package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "repairrecord")
public class RepairRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private WorkOrder workOrder;
    
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private MaintenanceStaff staff;
    
    @Column(name = "repair_date")
    private LocalDateTime repairDate;
    
    @Column(name = "repair_description", nullable = false, columnDefinition = "TEXT")
    private String repairDescription;
    
    @Column(name = "labor_hours", precision = 5, scale = 2)
    private BigDecimal laborHours = BigDecimal.ZERO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RepairStatus status = RepairStatus.STARTED;
    
    @Column(columnDefinition = "TEXT")
    private String notes;
    
    @PrePersist
    protected void onCreate() {
        repairDate = LocalDateTime.now();
    }
    
    public enum RepairStatus {
        STARTED, IN_PROGRESS, COMPLETED
    }
} 