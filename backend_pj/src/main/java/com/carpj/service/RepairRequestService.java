package com.carpj.service;

import com.carpj.model.RepairRequest;
import com.carpj.model.User;
import com.carpj.model.Vehicle;

import java.util.List;

/**
 * 维修申请服务接口
 */
public interface RepairRequestService {
    
    /**
     * 提交维修申请
     * @param repairRequest 维修申请
     * @return 提交成功的维修申请
     */
    RepairRequest submitRepairRequest(RepairRequest repairRequest);
    
    /**
     * 根据ID查找维修申请
     * @param id 维修申请ID
     * @return 维修申请对象
     */
    RepairRequest getRepairRequestById(Long id);
    
    /**
     * 获取所有维修申请
     * @return 维修申请列表
     */
    List<RepairRequest> getAllRepairRequests();
    
    /**
     * 获取特定用户的维修申请
     * @param user 用户
     * @return 维修申请列表
     */
    List<RepairRequest> getRepairRequestsByUser(User user);
    
    /**
     * 获取特定车辆的维修申请
     * @param vehicle 车辆
     * @return 维修申请列表
     */
    List<RepairRequest> getRepairRequestsByVehicle(Vehicle vehicle);
    
    /**
     * 获取特定状态的维修申请
     * @param status 状态
     * @return 维修申请列表
     */
    List<RepairRequest> getRepairRequestsByStatus(String status);
    
    /**
     * 更新维修申请
     * @param repairRequest 维修申请
     * @return 更新后的维修申请
     */
    RepairRequest updateRepairRequest(RepairRequest repairRequest);
    
    /**
     * 取消维修申请
     * @param id 维修申请ID
     * @return 操作成功返回true，否则返回false
     */
    boolean cancelRepairRequest(Long id);
    
    /**
     * 获取特定用户特定状态的维修申请
     * @param user 用户
     * @param status 状态
     * @return 维修申请列表
     */
    List<RepairRequest> getRepairRequestsByUserAndStatus(User user, String status);
    
    /**
     * 获取紧急维修申请
     * @return 紧急维修申请列表
     */
    List<RepairRequest> getUrgentRepairRequests();
} 