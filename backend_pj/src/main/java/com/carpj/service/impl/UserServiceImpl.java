package com.carpj.service.impl;

import com.carpj.model.User;
import com.carpj.model.Vehicle;
import com.carpj.repository.UserRepository;
import com.carpj.repository.VehicleRepository;
import com.carpj.service.AuthService;
import com.carpj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final AuthService authService;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, VehicleRepository vehicleRepository, AuthService authService) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
        this.authService = authService;
    }
    
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    
    @Override
    public User save(User user) {
        // 注意：此方法不处理密码，密码处理应该由AuthService完成
        return userRepository.save(user);
    }
    
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    
    @Override
    public List<Vehicle> getUserVehicles(Long userId) {
        User user = findById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return vehicleRepository.findByUser(user);
    }
    
    @Override
    public User updateUserInfo(Long userId, User userDetails) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            
            // 更新用户信息，但不修改敏感字段
            if (userDetails.getName() != null) {
                existingUser.setName(userDetails.getName());
            }
            if (userDetails.getPhone() != null) {
                existingUser.setPhone(userDetails.getPhone());
            }
            if (userDetails.getEmail() != null) {
                // 确保新邮箱不与其他用户冲突
                if (!userDetails.getEmail().equals(existingUser.getEmail()) && 
                    !authService.isEmailAvailable(userDetails.getEmail(), "user")) {
                    throw new IllegalArgumentException("该邮箱已被使用");
                }
                existingUser.setEmail(userDetails.getEmail());
            }
            if (userDetails.getAddress() != null) {
                existingUser.setAddress(userDetails.getAddress());
            }
            
            return userRepository.save(existingUser);
        }
        throw new IllegalArgumentException("用户不存在");
    }
} 