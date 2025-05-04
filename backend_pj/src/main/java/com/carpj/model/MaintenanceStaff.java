package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "maintenancestaff")
public class MaintenanceStaff {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Long id;
    
    @Column(nullable = false, length = 50, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 100)
    private String specialty;
    
    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 100, unique = true)
    private String email;
    
    @Column(name = "hire_date")
    private LocalDateTime hireDate;
    
    /**
     * 维修工种类型的可选值
     */
    public static final String[] SPECIALTY_TYPES = {
        "漆工", "焊工", "机修", "电工", "钣金", "装配", "诊断", "综合维修"
    };
    
    public Long getId() {
        return this.id;
    }
    
    @PrePersist
    protected void onCreate() {
        hireDate = LocalDateTime.now();
    }
} 