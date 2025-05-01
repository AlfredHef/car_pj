package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "staffpayment")
public class StaffPayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private MaintenanceStaff staff;
    
    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;
    
    @Column(name = "period_end", nullable = false)
    private LocalDate periodEnd;
    
    @Column(name = "hours_worked", nullable = false, precision = 10, scale = 2)
    private BigDecimal hoursWorked;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.PENDING;
    
    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDateTime.now();
    }
    
    public enum PaymentStatus {
        PENDING, COMPLETED
    }
} 