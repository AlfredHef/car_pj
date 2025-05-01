package com.carpj.model;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "administrator")
public class Administrator {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer id;
    
    @Column(nullable = false, length = 50, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 20)
    private String phone;
    
    @Column(length = 100, unique = true)
    private String email;
    
    @Column(name = "access_level")
    private Integer accessLevel = 1;
} 