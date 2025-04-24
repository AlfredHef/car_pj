package com.car.view;

import javax.swing.*;
import java.awt.*;

public class UserMainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public UserMainFrame() {
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
        
        // TODO: 实现个人信息面板
        panel.add(new JLabel("个人信息功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }
} 