package com.car.view;

import javax.swing.*;
import java.awt.*;

public class AdminMainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public AdminMainFrame() {
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
} 