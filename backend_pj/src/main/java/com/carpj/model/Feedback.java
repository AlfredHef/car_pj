package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private WorkOrder workOrder;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "rating", nullable = false)
    private Long rating;
    
    @Column(columnDefinition = "TEXT")
    private String comments;
    
    @Column(name = "feedback_date")
    private LocalDateTime feedbackDate;
    
    @Column(name = "is_urgent")
    private Boolean isUrgent = false;
    
    @PrePersist
    protected void onCreate() {
        feedbackDate = LocalDateTime.now();
    }
} 