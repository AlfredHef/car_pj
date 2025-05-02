package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "workorder")
public class WorkOrder {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false, referencedColumnName = "vehicle_id")
    private Vehicle vehicle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", referencedColumnName = "staff_id")
    private MaintenanceStaff staff;
    
    @Column(name = "issue_description", nullable = false, columnDefinition = "TEXT")
    private String issueDescription;
    
    @Column(name = "creation_date")
    private LocalDateTime creationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = OrderStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority = Priority.MEDIUM;
    
    @Column(name = "estimated_completion")
    private LocalDateTime estimatedCompletion;
    
    @Column(name = "actual_completion")
    private LocalDateTime actualCompletion;
    
    @Column(name = "total_labor_cost", precision = 10, scale = 2)
    private BigDecimal totalLaborCost = BigDecimal.ZERO;
    
    @Column(name = "total_parts_cost", precision = 10, scale = 2)
    private BigDecimal totalPartsCost = BigDecimal.ZERO;
    
    @Column(name = "total_cost", precision = 10, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;
    
    @PrePersist
    protected void onCreate() {
        creationDate = LocalDateTime.now();
    }
    
    public enum OrderStatus {
        PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED
    }
    
    public enum Priority {
        LOW, MEDIUM, HIGH
    }
} 