package com.car.view;

import com.carpj.model.User;
import javax.swing.*;
import java.awt.*;

public class UserMainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private User currentUser;

    public UserMainFrame() {
        this(null);
    }
    
    public UserMainFrame(User user) {
        this.currentUser = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("车辆维修管理系统 - 用户界面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 创建选项卡面板
        tabbedPane = new JTabbedPane();
        
        // 添加各个功能选项卡
        tabbedPane.addTab("车辆信息", createVehiclePanel());
        tabbedPane.addTab("维修申请", createRepairRequestPanel());
        tabbedPane.addTab("维修记录", createRepairHistoryPanel());
        tabbedPane.addTab("个人信息", createProfilePanel());

        add(tabbedPane);
        
        // 设置欢迎信息
        if (currentUser != null) {
            setTitle("车辆维修管理系统 - 用户界面 - 欢迎 " + currentUser.getName());
        }
    }

    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现车辆信息面板
        panel.add(new JLabel("车辆信息功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createRepairRequestPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现维修申请面板
        panel.add(new JLabel("维修申请功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createRepairHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现维修记录面板
        panel.add(new JLabel("维修记录功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        if (currentUser != null) {
            JPanel userInfoPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            userInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            userInfoPanel.add(new JLabel("用户名:"));
            userInfoPanel.add(new JLabel(currentUser.getUsername()));
            
            userInfoPanel.add(new JLabel("姓名:"));
            userInfoPanel.add(new JLabel(currentUser.getName()));
            
            userInfoPanel.add(new JLabel("电话:"));
            userInfoPanel.add(new JLabel(currentUser.getPhone() != null ? currentUser.getPhone() : ""));
            
            userInfoPanel.add(new JLabel("邮箱:"));
            userInfoPanel.add(new JLabel(currentUser.getEmail() != null ? currentUser.getEmail() : ""));
            
            userInfoPanel.add(new JLabel("地址:"));
            userInfoPanel.add(new JLabel(currentUser.getAddress() != null ? currentUser.getAddress() : ""));
            
            userInfoPanel.add(new JLabel("注册日期:"));
            userInfoPanel.add(new JLabel(currentUser.getRegistrationDate() != null ? 
                    currentUser.getRegistrationDate().toString() : ""));
            
            panel.add(userInfoPanel, BorderLayout.CENTER);
            
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
            panel.add(new JLabel("无法获取用户信息"), BorderLayout.CENTER);
        }
        
        return panel;
    }
} 