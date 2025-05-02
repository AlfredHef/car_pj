package com.carpj.model;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "materialusage")
public class MaterialUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usage_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "repair_id", nullable = false)
    private RepairRecord repairRecord;
    
    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
    
    @Column(name = "quantity", nullable = false)
    private Long quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;
    
    @Column(name = "usage_date")
    private LocalDateTime usageDate;
    
    @PrePersist
    protected void onCreate() {
        usageDate = LocalDateTime.now();
        // 自动计算成本
        if (material != null && material.getUnitPrice() != null) {
            cost = material.getUnitPrice().multiply(new BigDecimal(quantity));
        }
    }
} 