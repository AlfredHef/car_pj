package com.carpj.controller;

import com.carpj.model.User;
import com.carpj.model.Vehicle;
import com.carpj.service.UserService;
import com.carpj.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;

    @Autowired
    public VehicleController(VehicleService vehicleService, UserService userService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    /**
     * 根据ID获取车辆信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        log.info("获取车辆信息，ID: {}", id);
        Optional<Vehicle> vehicle = vehicleService.findById(id);
        return vehicle.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "车辆不存在"));
    }

    /**
     * 获取用户的所有车辆
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Vehicle>> getUserVehicles(@PathVariable Long userId) {
        log.info("获取用户车辆列表，用户ID: {}", userId);
        List<Vehicle> vehicles = vehicleService.findByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * 添加新车辆
     */
    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {
        log.info("添加新车辆, 车牌号: {}", vehicle.getLicensePlate());
        
        // 检查用户是否存在
        if (vehicle.getUser() == null || vehicle.getUser().getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "用户信息不能为空");
        }
        
        User user = userService.findById(vehicle.getUser().getId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "用户不存在");
        }
        
        try {
            Vehicle savedVehicle = vehicleService.save(vehicle);
            return new ResponseEntity<>(savedVehicle, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * 更新车辆信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable Long id, @RequestBody Vehicle vehicle) {
        log.info("更新车辆信息, ID: {}", id);
        
        try {
            Vehicle updatedVehicle = vehicleService.update(id, vehicle);
            return ResponseEntity.ok(updatedVehicle);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    /**
     * 删除车辆
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteVehicle(@PathVariable Long id) {
        log.info("删除车辆, ID: {}", id);
        
        boolean success = vehicleService.delete(id);
        if (success) {
            return ResponseEntity.ok(Map.of("success", true));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "车辆不存在或无法删除");
        }
    }

    /**
     * 根据车牌号查询车辆
     */
    @GetMapping("/licensePlate/{licensePlate}")
    public ResponseEntity<Vehicle> getVehicleByLicensePlate(@PathVariable String licensePlate) {
        log.info("根据车牌号查询车辆: {}", licensePlate);
        
        Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "车辆不存在");
    }

    /**
     * 检查车牌号是否可用
     */
    @GetMapping("/check-license-plate/{licensePlate}")
    public ResponseEntity<Map<String, Boolean>> checkLicensePlate(@PathVariable String licensePlate) {
        log.info("检查车牌号是否可用: {}", licensePlate);
        
        boolean exists = vehicleService.existsByLicensePlate(licensePlate);
        return ResponseEntity.ok(Map.of("available", !exists));
    }
} 