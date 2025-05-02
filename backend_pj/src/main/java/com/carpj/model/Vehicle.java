package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    private User user;
    
    @Column(name = "license_plate", nullable = false, unique = true, length = 20)
    private String licensePlate;
    
    @Column(nullable = false, length = 50)
    private String brand;
    
    @Column(nullable = false, length = 50)
    private String model;
    
    private Integer year;
    
    @Column(length = 30)
    private String color;
    
    @Column(unique = true, length = 50)
    private String vin;
    
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    
    @PrePersist
    protected void onCreate() {
        registrationDate = LocalDateTime.now();
    }
} 