package com.carpj.service.impl;

import com.carpj.model.Administrator;
import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;
import com.carpj.repository.AdministratorRepository;
import com.carpj.repository.MaintenanceStaffRepository;
import com.carpj.repository.UserRepository;
import com.carpj.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final MaintenanceStaffRepository staffRepository;
    private final AdministratorRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, 
                          MaintenanceStaffRepository staffRepository, 
                          AdministratorRepository adminRepository) {
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User userLogin(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && validatePassword(password, user.getPassword())) {
            log.info("用户登录成功: {}", username);
            return user;
        }
        log.warn("用户登录失败: {}", username);
        return null;
    }

    @Override
    public User userRegister(User user) {
        // 检查用户名和邮箱是否可用
        if (!isUsernameAvailable(user.getUsername(), "user")) {
            log.warn("注册失败: 用户名已存在 {}", user.getUsername());
            throw new IllegalArgumentException("用户名已存在");
        }
        
        if (user.getEmail() != null && !isEmailAvailable(user.getEmail(), "user")) {
            log.warn("注册失败: 邮箱已存在 {}", user.getEmail());
            throw new IllegalArgumentException("邮箱已存在");
        }
        
        // 加密密码
        user.setPassword(encodePassword(user.getPassword()));
        
        // 设置注册时间
        if (user.getRegistrationDate() == null) {
            user.setRegistrationDate(LocalDateTime.now());
        }
        
        // 保存用户
        User savedUser = userRepository.save(user);
        log.info("用户注册成功: {}", user.getUsername());
        return savedUser;
    }

    @Override
    public MaintenanceStaff staffLogin(String username, String password) {
        MaintenanceStaff staff = staffRepository.findByUsername(username);
        if (staff != null && validatePassword(password, staff.getPassword())) {
            log.info("维修人员登录成功: {}", username);
            return staff;
        }
        log.warn("维修人员登录失败: {}", username);
        return null;
    }

    @Override
    public Administrator adminLogin(String username, String password) {
        Administrator admin = adminRepository.findByUsername(username);
        if (admin != null && validatePassword(password, admin.getPassword())) {
            log.info("管理员登录成功: {}", username);
            return admin;
        }
        log.warn("管理员登录失败: {}", username);
        return null;
    }

    @Override
    public boolean isUsernameAvailable(String username, String userType) {
        switch (userType.toLowerCase()) {
            case "user":
                return !userRepository.existsByUsername(username);
            case "staff":
                return !staffRepository.existsByUsername(username);
            case "admin":
                return !adminRepository.existsByUsername(username);
            default:
                throw new IllegalArgumentException("无效的用户类型");
        }
    }

    @Override
    public boolean isEmailAvailable(String email, String userType) {
        switch (userType.toLowerCase()) {
            case "user":
                return !userRepository.existsByEmail(email);
            case "staff":
                return !staffRepository.existsByEmail(email);
            case "admin":
                return !adminRepository.existsByEmail(email);
            default:
                throw new IllegalArgumentException("无效的用户类型");
        }
    }
    
    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
    
    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    @Override
    public boolean changeUserPassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            
            // 验证旧密码
            if (validatePassword(oldPassword, user.getPassword())) {
                // 设置新密码
                user.setPassword(encodePassword(newPassword));
                userRepository.save(user);
                log.info("用户 {} 密码更改成功", user.getUsername());
                return true;
            }
            log.warn("用户 {} 密码更改失败：旧密码验证失败", user.getUsername());
        } else {
            log.warn("密码更改失败：用户ID {}不存在", userId);
        }
        return false;
    }
} 