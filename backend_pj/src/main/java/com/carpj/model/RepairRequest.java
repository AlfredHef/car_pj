package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "repairrequest")
public class RepairRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;
    
    @Column(name = "issue_description", nullable = false, columnDefinition = "TEXT")
    private String issueDescription;
    
    @Column(name = "request_date")
    private LocalDateTime requestDate;
    
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;
    
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;
    
    @Column(name = "preferred_date")
    private LocalDateTime preferredDate;
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "is_urgent", nullable = false)
    private boolean urgent = false;
    
    /**
     * 维修申请状态
     */
    public enum RequestStatus {
        PENDING,           // 待处理
        APPROVED,          // 已批准
        ASSIGNED,          // 已分配
        IN_PROGRESS,       // 进行中
        COMPLETED,         // 已完成
        CANCELLED          // 已取消
    }
    
    /**
     * 优先级
     */
    public enum Priority {
        LOW,               // 低
        MEDIUM,            // 中
        HIGH               // 高
    }
    
    @PrePersist
    protected void onCreate() {
        requestDate = LocalDateTime.now();
    }
    
    public Long getId() {
        return this.id;
    }
} 