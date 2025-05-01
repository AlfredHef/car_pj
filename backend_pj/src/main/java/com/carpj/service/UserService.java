package com.carpj.service;

import com.carpj.model.User;
import com.carpj.model.Vehicle;

import java.util.List;

/**
 * 用户服务接口，处理用户信息管理和相关业务功能
 */
public interface UserService {
    /**
     * 根据ID查找用户
     */
    User findById(Long id);
    
    /**
     * 根据用户名查找用户
     */
    User findByUsername(String username);
    
    /**
     * 查找所有用户
     */
    List<User> findAll();
    
    /**
     * 保存或更新用户信息（不包含密码修改）
     */
    User save(User user);
    
    /**
     * 删除用户
     */
    void deleteById(Long id);
    
    /**
     * 获取用户的车辆
     */
    List<Vehicle> getUserVehicles(Long userId);
    
    /**
     * 更新用户个人信息（不包含密码）
     */
    User updateUserInfo(Long userId, User userDetails);
} 