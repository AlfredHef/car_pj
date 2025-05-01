package com.car.view;

import com.carpj.model.Administrator;
import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private Administrator currentAdmin;

    public AdminMainFrame() {
        this(null);
    }
    
    public AdminMainFrame(Administrator admin) {
        this.currentAdmin = admin;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("车辆维修管理系统 - 管理员界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 创建选项卡面板
        tabbedPane = new JTabbedPane();
        
        // 添加各个功能选项卡
        tabbedPane.addTab("用户管理", createUserManagementPanel());
        tabbedPane.addTab("维修人员管理", createStaffManagementPanel());
        tabbedPane.addTab("工单管理", createWorkOrderPanel());
        tabbedPane.addTab("数据统计", createStatisticsPanel());
        tabbedPane.addTab("系统设置", createSystemSettingsPanel());

        add(tabbedPane);
        
        // 设置欢迎信息
        if (currentAdmin != null) {
            setTitle("车辆维修管理系统 - 管理员界面 - 欢迎 " + currentAdmin.getName());
        }
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现用户管理面板
        panel.add(new JLabel("用户管理功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createStaffManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现维修人员管理面板
        panel.add(new JLabel("维修人员管理功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createWorkOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现工单管理面板
        panel.add(new JLabel("工单管理功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现数据统计面板
        panel.add(new JLabel("数据统计功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createSystemSettingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现系统设置面板
        panel.add(new JLabel("系统设置功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        if (currentAdmin != null) {
            JPanel adminInfoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            adminInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            adminInfoPanel.add(new JLabel("用户名:"));
            adminInfoPanel.add(new JLabel(currentAdmin.getUsername()));
            
            adminInfoPanel.add(new JLabel("姓名:"));
            adminInfoPanel.add(new JLabel(currentAdmin.getName()));
            
            adminInfoPanel.add(new JLabel("权限级别:"));
            adminInfoPanel.add(new JLabel(String.valueOf(currentAdmin.getAccessLevel())));
            
            adminInfoPanel.add(new JLabel("电话:"));
            adminInfoPanel.add(new JLabel(currentAdmin.getPhone() != null ? currentAdmin.getPhone() : ""));
            
            adminInfoPanel.add(new JLabel("邮箱:"));
            adminInfoPanel.add(new JLabel(currentAdmin.getEmail() != null ? currentAdmin.getEmail() : ""));
            
            panel.add(adminInfoPanel, BorderLayout.CENTER);
            
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton editButton = new JButton("编辑管理员信息");
            editButton.addActionListener(e -> {
                // TODO: 实现编辑管理员信息功能
                JOptionPane.showMessageDialog(this, 
                    "编辑管理员信息功能待实现", 
                    "功能开发中", JOptionPane.INFORMATION_MESSAGE);
            });
            buttonPanel.add(editButton);
            
            panel.add(buttonPanel, BorderLayout.SOUTH);
        } else {
            panel.add(new JLabel("无法获取管理员信息"), BorderLayout.CENTER);
        }
        
        return panel;
    }
} 