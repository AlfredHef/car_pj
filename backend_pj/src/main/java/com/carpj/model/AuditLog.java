package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "auditlog")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long id;
    
    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;
    
    @Column(name = "entity_type", nullable = false, length = 50)
    private String entityType;
    
    @Column(name = "entity_id", nullable = false)
    private Long entityId;
    
    @Column(name = "user_type", nullable = false, length = 20)
    private String userType;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "action_date")
    private LocalDateTime actionDate;
    
    @Column(columnDefinition = "TEXT")
    private String details;
    
    @Column(name = "ip_address", length = 50)
    private String ipAddress;
    
    @PrePersist
    protected void onCreate() {
        actionDate = LocalDateTime.now();
    }
} 