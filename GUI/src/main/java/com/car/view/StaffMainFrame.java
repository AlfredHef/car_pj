package com.car.view;

import com.carpj.model.MaintenanceStaff;
import javax.swing.*;
import java.awt.*;

public class StaffMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private MaintenanceStaff currentStaff;

    public StaffMainFrame() {
        this(null);
    }
    
    public StaffMainFrame(MaintenanceStaff staff) {
        this.currentStaff = staff;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("车辆维修管理系统 - 维修人员界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 创建选项卡面板
        tabbedPane = new JTabbedPane();
        
        // 添加各个功能选项卡
        tabbedPane.addTab("待处理工单", createPendingOrdersPanel());
        tabbedPane.addTab("进行中工单", createInProgressOrdersPanel());
        tabbedPane.addTab("完成的工单", createCompletedOrdersPanel());
        tabbedPane.addTab("个人信息", createProfilePanel());

        add(tabbedPane);
        
        // 设置欢迎信息
        if (currentStaff != null) {
            setTitle("车辆维修管理系统 - 维修人员界面 - 欢迎 " + currentStaff.getName());
        }
    }

    private JPanel createPendingOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现待处理工单面板
        panel.add(new JLabel("待处理工单功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createInProgressOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现进行中工单面板
        panel.add(new JLabel("进行中工单功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createCompletedOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现完成的工单面板
        panel.add(new JLabel("完成的工单功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        if (currentStaff != null) {
            JPanel staffInfoPanel = new JPanel(new GridLayout(7, 2, 10, 10));
            staffInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            staffInfoPanel.add(new JLabel("用户名:"));
            staffInfoPanel.add(new JLabel(currentStaff.getUsername()));
            
            staffInfoPanel.add(new JLabel("姓名:"));
            staffInfoPanel.add(new JLabel(currentStaff.getName()));
            
            staffInfoPanel.add(new JLabel("专业领域:"));
            staffInfoPanel.add(new JLabel(currentStaff.getSpecialty()));
            
            staffInfoPanel.add(new JLabel("小时收费:"));
            staffInfoPanel.add(new JLabel(String.valueOf(currentStaff.getHourlyRate())));
            
            staffInfoPanel.add(new JLabel("电话:"));
            staffInfoPanel.add(new JLabel(currentStaff.getPhone() != null ? currentStaff.getPhone() : ""));
            
            staffInfoPanel.add(new JLabel("邮箱:"));
            staffInfoPanel.add(new JLabel(currentStaff.getEmail() != null ? currentStaff.getEmail() : ""));
            
            staffInfoPanel.add(new JLabel("入职日期:"));
            staffInfoPanel.add(new JLabel(currentStaff.getHireDate() != null ? 
                    currentStaff.getHireDate().toString() : ""));
            
            panel.add(staffInfoPanel, BorderLayout.CENTER);
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton editButton = new JButton("编辑个人信息");
            editButton.addActionListener(e -> {
                // TODO: 实现编辑个人信息功能
                JOptionPane.showMessageDialog(this, 
                    "编辑个人信息功能待实现", 
                    "功能开发中", JOptionPane.INFORMATION_MESSAGE);
            });
            buttonPanel.add(editButton);
            
            panel.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            panel.add(new JLabel("无法获取维修人员信息"), BorderLayout.CENTER);
        }
        
        return panel;
    }
} 