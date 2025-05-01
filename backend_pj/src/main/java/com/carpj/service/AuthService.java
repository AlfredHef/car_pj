package com.carpj.service;

import com.carpj.model.Administrator;
import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;

/**
 * 认证服务接口，处理用户认证、授权和注册
 */
public interface AuthService {
    
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回用户对象，失败返回null
     */
    User userLogin(String username, String password);
    
    /**
     * 用户注册
     * @param user 用户信息
     * @return 注册成功返回用户对象
     */
    User userRegister(User user);
    
    /**
     * 维修人员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回维修人员对象，失败返回null
     */
    MaintenanceStaff staffLogin(String username, String password);
    
    /**
     * 管理员登录
     * @param username 用户名
     * @param password 密码
     * @return 登录成功返回管理员对象，失败返回null
     */
    Administrator adminLogin(String username, String password);
    
    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @param userType 用户类型：user, staff, admin
     * @return 可用返回true，已存在返回false
     */
    boolean isUsernameAvailable(String username, String userType);
    
    /**
     * 检查邮箱是否可用
     * @param email 邮箱
     * @param userType 用户类型：user, staff, admin
     * @return 可用返回true，已存在返回false
     */
    boolean isEmailAvailable(String email, String userType);
    
    /**
     * 验证用户密码
     * @param rawPassword 原始密码
     * @param encodedPassword 编码后的密码
     * @return 验证成功返回true，失败返回false
     */
    boolean validatePassword(String rawPassword, String encodedPassword);
    
    /**
     * 加密密码
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    String encodePassword(String rawPassword);
    
    /**
     * 更改用户密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 更改成功返回true，失败返回false
     */
    boolean changeUserPassword(Long userId, String oldPassword, String newPassword);
} 