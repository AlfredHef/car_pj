package com.car.service;

import com.carpj.model.*;
import com.carpj.service.AuthService;
import com.carpj.service.RepairRequestService;
import com.carpj.service.UserService;
import com.carpj.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 此服务类用于GUI与后端之间的交互
 * 它使用后端的服务类来执行操作
 */
@Service
public class BackendService {
    private static final Logger logger = LoggerFactory.getLogger(BackendService.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private RepairRequestService repairRequestService;
    
    @Autowired
    private VehicleService vehicleService;
    
    @PostConstruct
    public void init() {
        logger.info("BackendService初始化完成");
        logger.info("UserService: {}", userService != null ? "已注入" : "未注入");
        logger.info("AuthService: {}", authService != null ? "已注入" : "未注入");
        logger.info("RepairRequestService: {}", repairRequestService != null ? "已注入" : "未注入");
        logger.info("VehicleService: {}", vehicleService != null ? "已注入" : "未注入");
    }

    /**
     * 用户登录验证
     * @param username 用户名
     * @param password 密码
     * @return 如果验证成功返回用户对象，否则返回null
     */
    public User login(String username, String password) {
        logger.info("尝试用户登录: {}", username);
        if (authService == null) {
            logger.error("AuthService未初始化");
            return null;
        }
        return authService.userLogin(username, password);
    }
    
    /**
     * 维修人员登录验证
     * @param username 用户名
     * @param password 密码
     * @return 如果验证成功返回维修人员对象，否则返回null
     */
    public MaintenanceStaff staffLogin(String username, String password) {
        logger.info("尝试维修人员登录: {}", username);
        if (authService == null) {
            logger.error("AuthService未初始化");
            return null;
        }
        return authService.staffLogin(username, password);
    }
    
    /**
     * 管理员登录验证
     * @param username 用户名
     * @param password 密码
     * @return 如果验证成功返回管理员对象，否则返回null
     */
    public Administrator adminLogin(String username, String password) {
        logger.info("尝试管理员登录: {}", username);
        if (authService == null) {
            logger.error("AuthService未初始化");
            return null;
        }
        return authService.adminLogin(username, password);
    }

    /**
     * 用户注册
     * @param user 要注册的用户对象
     * @return 注册成功的用户对象
     */
    public User register(User user) {
        logger.info("尝试注册用户: {}", user.getUsername());
        if (authService == null) {
            logger.error("AuthService未初始化");
            return null;
        }
        return authService.userRegister(user);
    }
    
    /**
     * 维修人员注册
     * @param staff 要注册的维修人员对象
     * @return 注册成功的维修人员对象
     */
    public MaintenanceStaff registerStaff(MaintenanceStaff staff) {
        logger.info("尝试注册维修人员: {}", staff.getUsername());
        if (authService == null) {
            logger.error("AuthService未初始化");
            return null;
        }
        return authService.staffRegister(staff);
    }
    
    /**
     * 检查用户名是否可用
     * @param username 用户名
     * @param userType 用户类型
     * @return 如果可用返回true，否则返回false
     */
    public boolean isUsernameAvailable(String username, String userType) {
        if (authService == null) {
            logger.error("AuthService未初始化");
            return false;
        }
        return authService.isUsernameAvailable(username, userType);
    }
    
    /**
     * 检查邮箱是否可用
     * @param email 邮箱
     * @param userType 用户类型
     * @return 如果可用返回true，否则返回false
     */
    public boolean isEmailAvailable(String email, String userType) {
        if (authService == null) {
            logger.error("AuthService未初始化");
            return false;
        }
        return authService.isEmailAvailable(email, userType);
    }
    
    /**
     * 提交维修申请
     * @param repairRequest 维修申请
     * @return 提交成功的维修申请
     */
    public RepairRequest submitRepairRequest(RepairRequest repairRequest) {
        if (repairRequestService == null) {
            logger.error("RepairRequestService未初始化");
            return null;
        }
        return repairRequestService.submitRepairRequest(repairRequest);
    }
    
    /**
     * 获取用户的所有维修申请
     * @param user 用户
     * @return 维修申请列表
     */
    public List<RepairRequest> getUserRepairRequests(User user) {
        if (repairRequestService == null) {
            logger.error("RepairRequestService未初始化");
            return null;
        }
        return repairRequestService.getRepairRequestsByUser(user);
    }
    
    /**
     * 获取特定状态的维修申请
     * @param status 状态
     * @return 维修申请列表
     */
    public List<RepairRequest> getRepairRequestsByStatus(String status) {
        if (repairRequestService == null) {
            logger.error("RepairRequestService未初始化");
            return null;
        }
        return repairRequestService.getRepairRequestsByStatus(status);
    }
    
    /**
     * 取消维修申请
     * @param requestId 申请ID
     * @return 取消成功返回true，否则返回false
     */
    public boolean cancelRepairRequest(Long requestId) {
        if (repairRequestService == null) {
            logger.error("RepairRequestService未初始化");
            return false;
        }
        return repairRequestService.cancelRepairRequest(requestId);
    }
    
    /**
     * 获取用户的车辆列表
     * @param userId 用户ID
     * @return 车辆列表
     */
    public List<Vehicle> getUserVehicles(Long userId) {
        logger.info("获取用户车辆列表, 用户ID: {}", userId);
        if (vehicleService == null) {
            logger.error("VehicleService未初始化");
            return null;
        }
        try {
            return vehicleService.findByUserId(userId);
        } catch (Exception e) {
            logger.error("获取用户车辆列表失败: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 添加新车辆
     * @param vehicle 车辆信息
     * @return 添加成功的车辆对象
     */
    public Vehicle addVehicle(Vehicle vehicle) {
        logger.info("尝试添加车辆，车牌号: {}", vehicle.getLicensePlate());
        if (vehicleService == null) {
            logger.error("VehicleService未初始化");
            return null;
        }
        
        try {
            // 首先检查用户是否存在
            if (vehicle.getUser() == null || vehicle.getUser().getId() == null) {
                logger.error("添加车辆时用户信息为空");
                return null;
            }
            
            // 通过后端服务保存车辆
            return vehicleService.save(vehicle);
        } catch (Exception e) {
            logger.error("添加车辆时发生错误: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 更新车辆信息
     * @param vehicle 更新后的车辆信息
     * @return 更新成功的车辆对象
     */
    public Vehicle updateVehicle(Vehicle vehicle) {
        logger.info("尝试更新车辆信息，ID: {}", vehicle.getId());
        if (vehicleService == null) {
            logger.error("VehicleService未初始化");
            return null;
        }
        
        try {
            // 通过后端服务更新车辆
            return vehicleService.update(vehicle.getId(), vehicle);
        } catch (Exception e) {
            logger.error("更新车辆信息时发生错误: {}", e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 删除车辆
     * @param vehicleId 车辆ID
     * @return 删除成功返回true，否则返回false
     */
    public boolean deleteVehicle(Long vehicleId) {
        logger.info("尝试删除车辆，ID: {}", vehicleId);
        if (vehicleService == null) {
            logger.error("VehicleService未初始化");
            return false;
        }
        
        try {
            // 通过后端服务删除车辆
            return vehicleService.delete(vehicleId);
        } catch (Exception e) {
            logger.error("删除车辆时发生错误: {}", e.getMessage(), e);
            return false;
        }
    }
} 