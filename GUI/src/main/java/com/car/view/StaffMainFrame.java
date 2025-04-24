package com.car.view;

import javax.swing.*;
import java.awt.*;

public class StaffMainFrame extends JFrame {
    private JTabbedPane tabbedPane;

    public StaffMainFrame() {
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
        tabbedPane.addTab("工单管理", createWorkOrderPanel());
        tabbedPane.addTab("材料管理", createMaterialPanel());
        tabbedPane.addTab("工时记录", createTimeRecordPanel());
        tabbedPane.addTab("个人信息", createProfilePanel());

        add(tabbedPane);
    }

    private JPanel createWorkOrderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现工单管理面板
        panel.add(new JLabel("工单管理功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createMaterialPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现材料管理面板
        panel.add(new JLabel("材料管理功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createTimeRecordPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现工时记录面板
        panel.add(new JLabel("工时记录功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel createProfilePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // TODO: 实现个人信息面板
        panel.add(new JLabel("个人信息功能待实现"), BorderLayout.CENTER);
        
        return panel;
    }
} 