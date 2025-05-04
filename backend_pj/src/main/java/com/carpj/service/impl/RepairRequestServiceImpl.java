package com.carpj.service.impl;

import com.carpj.model.RepairRequest;
import com.carpj.model.User;
import com.carpj.model.Vehicle;
import com.carpj.repository.RepairRequestRepository;
import com.carpj.service.RepairRequestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RepairRequestServiceImpl implements RepairRequestService {
    
    private final RepairRequestRepository repairRequestRepository;
    
    @Autowired
    public RepairRequestServiceImpl(RepairRequestRepository repairRequestRepository) {
        this.repairRequestRepository = repairRequestRepository;
    }
    
    @Override
    @Transactional
    public RepairRequest submitRepairRequest(RepairRequest repairRequest) {
        try {
            // 设置请求日期
            if (repairRequest.getRequestDate() == null) {
                repairRequest.setRequestDate(LocalDateTime.now());
            }
            
            // 设置默认状态
            if (repairRequest.getStatus() == null) {
                repairRequest.setStatus(RepairRequest.RequestStatus.PENDING);
            }
            
            // 保存请求
            RepairRequest savedRequest = repairRequestRepository.save(repairRequest);
            log.info("维修申请已提交: ID={}, 用户={}, 车辆={}",
                    savedRequest.getId(), 
                    savedRequest.getUser().getUsername(),
                    savedRequest.getVehicle().getLicensePlate());
            return savedRequest;
        } catch (Exception e) {
            log.error("提交维修申请失败", e);
            throw e;
        }
    }
    
    @Override
    public RepairRequest getRepairRequestById(Long id) {
        Optional<RepairRequest> request = repairRequestRepository.findById(id);
        if (request.isPresent()) {
            return request.get();
        }
        log.warn("未找到维修申请: ID={}", id);
        return null;
    }
    
    @Override
    public List<RepairRequest> getAllRepairRequests() {
        return repairRequestRepository.findAll();
    }
    
    @Override
    public List<RepairRequest> getRepairRequestsByUser(User user) {
        return repairRequestRepository.findByUser(user);
    }
    
    @Override
    public List<RepairRequest> getRepairRequestsByVehicle(Vehicle vehicle) {
        return repairRequestRepository.findByVehicle(vehicle);
    }
    
    @Override
    public List<RepairRequest> getRepairRequestsByStatus(String status) {
        try {
            RepairRequest.RequestStatus requestStatus = RepairRequest.RequestStatus.valueOf(status);
            return repairRequestRepository.findByStatus(requestStatus);
        } catch (IllegalArgumentException e) {
            log.error("无效的维修申请状态: {}", status, e);
            return List.of();
        }
    }
    
    @Override
    public RepairRequest updateRepairRequest(RepairRequest repairRequest) {
        if (repairRequest.getId() == null) {
            log.error("无法更新没有ID的维修申请");
            throw new IllegalArgumentException("维修申请ID不能为空");
        }
        
        if (!repairRequestRepository.existsById(repairRequest.getId())) {
            log.error("要更新的维修申请不存在: ID={}", repairRequest.getId());
            throw new IllegalArgumentException("维修申请不存在: ID=" + repairRequest.getId());
        }
        
        return repairRequestRepository.save(repairRequest);
    }
    
    @Override
    public boolean cancelRepairRequest(Long id) {
        Optional<RepairRequest> requestOpt = repairRequestRepository.findById(id);
        if (requestOpt.isEmpty()) {
            log.warn("要取消的维修申请不存在: ID={}", id);
            return false;
        }
        
        RepairRequest request = requestOpt.get();
        // 只能取消未开始的申请
        if (request.getStatus() == RepairRequest.RequestStatus.IN_PROGRESS || 
            request.getStatus() == RepairRequest.RequestStatus.COMPLETED) {
            log.warn("无法取消已开始或已完成的维修申请: ID={}, 状态={}", id, request.getStatus());
            return false;
        }
        
        request.setStatus(RepairRequest.RequestStatus.CANCELLED);
        repairRequestRepository.save(request);
        log.info("维修申请已取消: ID={}", id);
        return true;
    }
    
    @Override
    public List<RepairRequest> getRepairRequestsByUserAndStatus(User user, String status) {
        try {
            RepairRequest.RequestStatus requestStatus = RepairRequest.RequestStatus.valueOf(status);
            return repairRequestRepository.findByUserAndStatus(user, requestStatus);
        } catch (IllegalArgumentException e) {
            log.error("无效的维修申请状态: {}", status, e);
            return List.of();
        }
    }
    
    @Override
    public List<RepairRequest> getUrgentRepairRequests() {
        return repairRequestRepository.findByUrgent(true);
    }
} 