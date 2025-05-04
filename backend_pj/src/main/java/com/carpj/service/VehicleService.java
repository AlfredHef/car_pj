package com.carpj.service;

import com.carpj.model.User;
import com.carpj.model.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * 车辆服务接口，处理车辆信息管理和相关业务功能
 */
public interface VehicleService {
    /**
     * 根据ID查找车辆
     * @param id 车辆ID
     * @return 车辆对象
     */
    Optional<Vehicle> findById(Long id);
    
    /**
     * 根据车牌号查找车辆
     * @param licensePlate 车牌号
     * @return 车辆对象
     */
    Vehicle findByLicensePlate(String licensePlate);
    
    /**
     * 根据VIN码查找车辆
     * @param vin VIN码
     * @return 车辆对象
     */
    Vehicle findByVin(String vin);
    
    /**
     * 获取用户的所有车辆
     * @param user 用户对象
     * @return 车辆列表
     */
    List<Vehicle> findByUser(User user);
    
    /**
     * 获取用户的所有车辆
     * @param userId 用户ID
     * @return 车辆列表
     */
    List<Vehicle> findByUserId(Long userId);
    
    /**
     * 添加车辆
     * @param vehicle 车辆对象
     * @return 保存后的车辆对象
     */
    Vehicle save(Vehicle vehicle);
    
    /**
     * 更新车辆信息
     * @param id 车辆ID
     * @param vehicleDetails 更新的车辆信息
     * @return 更新后的车辆对象
     */
    Vehicle update(Long id, Vehicle vehicleDetails);
    
    /**
     * 删除车辆
     * @param id 车辆ID
     * @return 是否删除成功
     */
    boolean delete(Long id);
    
    /**
     * 检查车牌号是否已存在
     * @param licensePlate 车牌号
     * @return 是否存在
     */
    boolean existsByLicensePlate(String licensePlate);
} 