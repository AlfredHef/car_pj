package com.car.service;

import com.carpj.model.Administrator;
import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;
import com.carpj.service.AuthService;
import com.carpj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 此服务类用于GUI与后端之间的交互
 * 它使用后端的服务类来执行操作
 */
@Service
public class BackendService {

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 如果验证成功返回用户对象，否则返回null
     */
    public User login(String username, String password) {
        return authService.userLogin(username, password);
    }
    
    /**
     * 维修人员登录验证
     * @param username 用户名
     * @param password 密码
     * @return 如果验证成功返回维修人员对象，否则返回null
     */
    public MaintenanceStaff staffLogin(String username, String password) {
        return authService.staffLogin(username, password);
    }
    
    /**
     * 管理员登录验证
     * @param username 用户名
     * @param password 密码
     * @return 如果验证成功返回管理员对象，否则返回null
     */
    public Administrator adminLogin(String username, String password) {
        return authService.adminLogin(username, password);
    }

    /**
     * 用户注册
     * @param user 要注册的用户对象
     * @return 注册成功的用户对象
     */
    public User register(User user) {
        return authService.userRegister(user);
    }
    
    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @param userType 用户类型
     * @return 如果可用返回true，否则返回false
     */
    public boolean isUsernameAvailable(String username, String userType) {
        return authService.isUsernameAvailable(username, userType);
    }
    
    /**
     * 检查邮箱是否可用
     * @param email 邮箱
     * @param userType 用户类型
     * @return 如果可用返回true，否则返回false
     */
    public boolean isEmailAvailable(String email, String userType) {
        return authService.isEmailAvailable(email, userType);
    }
} 