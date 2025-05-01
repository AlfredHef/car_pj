package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "paymentrecord")
public class PaymentRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private WorkOrder workOrder;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status = PaymentStatus.PENDING;
    
    @Column(name = "transaction_id", length = 100)
    private String transactionId;
    
    @PrePersist
    protected void onCreate() {
        paymentDate = LocalDateTime.now();
    }
    
    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED
    }
} 