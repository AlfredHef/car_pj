package com.carpj.service.impl;

import com.carpj.model.User;
import com.carpj.model.Vehicle;
import com.carpj.repository.UserRepository;
import com.carpj.repository.VehicleRepository;
import com.carpj.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicleRepository.findByLicensePlate(licensePlate);
    }

    @Override
    public Vehicle findByVin(String vin) {
        return vehicleRepository.findByVin(vin);
    }

    @Override
    public List<Vehicle> findByUser(User user) {
        return vehicleRepository.findByUser(user);
    }

    @Override
    public List<Vehicle> findByUserId(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return vehicleRepository.findByUser(userOptional.get());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public Vehicle save(Vehicle vehicle) {
        log.info("添加新车辆: {}", vehicle.getLicensePlate());
        
        // 检查车牌号是否已存在
        if (existsByLicensePlate(vehicle.getLicensePlate())) {
            log.error("车牌号已存在: {}", vehicle.getLicensePlate());
            throw new IllegalArgumentException("车牌号已存在: " + vehicle.getLicensePlate());
        }
        
        // 如果VIN码不为空，检查是否已存在
        if (vehicle.getVin() != null && !vehicle.getVin().isEmpty()) {
            Vehicle existingVehicle = findByVin(vehicle.getVin());
            if (existingVehicle != null) {
                log.error("VIN码已存在: {}", vehicle.getVin());
                throw new IllegalArgumentException("VIN码已存在: " + vehicle.getVin());
            }
        }
        
        return vehicleRepository.save(vehicle);
    }

    @Override
    @Transactional
    public Vehicle update(Long id, Vehicle vehicleDetails) {
        log.info("更新车辆信息, ID: {}", id);
        
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (!vehicleOptional.isPresent()) {
            log.error("车辆不存在, ID: {}", id);
            throw new IllegalArgumentException("车辆不存在");
        }
        
        Vehicle existingVehicle = vehicleOptional.get();
        
        // 不允许修改车牌号
        if (!existingVehicle.getLicensePlate().equals(vehicleDetails.getLicensePlate())) {
            log.error("不允许修改车牌号");
            throw new IllegalArgumentException("不允许修改车牌号");
        }
        
        // 如果VIN码变更，需要检查是否已存在
        if (vehicleDetails.getVin() != null && 
            !vehicleDetails.getVin().equals(existingVehicle.getVin())) {
            Vehicle vehicleWithVin = findByVin(vehicleDetails.getVin());
            if (vehicleWithVin != null) {
                log.error("VIN码已存在: {}", vehicleDetails.getVin());
                throw new IllegalArgumentException("VIN码已存在");
            }
        }
        
        // 更新车辆信息
        if (vehicleDetails.getBrand() != null) {
            existingVehicle.setBrand(vehicleDetails.getBrand());
        }
        if (vehicleDetails.getModel() != null) {
            existingVehicle.setModel(vehicleDetails.getModel());
        }
        if (vehicleDetails.getYear() != null) {
            existingVehicle.setYear(vehicleDetails.getYear());
        }
        if (vehicleDetails.getColor() != null) {
            existingVehicle.setColor(vehicleDetails.getColor());
        }
        if (vehicleDetails.getVin() != null) {
            existingVehicle.setVin(vehicleDetails.getVin());
        }
        
        return vehicleRepository.save(existingVehicle);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        log.info("删除车辆, ID: {}", id);
        
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (!vehicleOptional.isPresent()) {
            log.error("车辆不存在, ID: {}", id);
            return false;
        }
        
        try {
            vehicleRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("删除车辆失败, ID: {}", id, e);
            return false;
        }
    }

    @Override
    public boolean existsByLicensePlate(String licensePlate) {
        return vehicleRepository.existsByLicensePlate(licensePlate);
    }
} 