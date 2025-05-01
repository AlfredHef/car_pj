package com.car.view;

import com.car.service.BackendService;
import com.carpj.model.Administrator;
import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;

@Component
public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;
    
    @Autowired
    private BackendService backendService;
    
    @Autowired
    private RegisterDialog registerDialog;

    public LoginFrame() {
        initializeUI();
    }
    
    @PostConstruct
    private void init() {
        // Spring注入完成后的初始化
    }
    
    public void setBackendService(BackendService backendService) {
        this.backendService = backendService;
    }

    private void initializeUI() {
        setTitle("车辆维修管理系统 - 登录");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 用户类型选择
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(new JLabel("用户类型:"), gbc);
        gbc.gridx = 1;
        userTypeCombo = new JComboBox<>(new String[]{"用户", "维修人员", "管理员"});
        mainPanel.add(userTypeCombo, gbc);

        // 用户名输入
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(new JLabel("用户名:"), gbc);
        gbc.gridx = 1;
        usernameField = new JTextField(20);
        mainPanel.add(usernameField, gbc);

        // 密码输入
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("密码:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        mainPanel.add(passwordField, gbc);

        // 登录按钮
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton, gbc);

        // 注册按钮
        gbc.gridy = 4;
        JButton registerButton = new JButton("注册新用户");
        registerButton.addActionListener(e -> showRegisterDialog());
        mainPanel.add(registerButton, gbc);

        add(mainPanel);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "用户名和密码不能为空", "登录失败", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (backendService == null) {
                JOptionPane.showMessageDialog(this, 
                    "后端服务尚未初始化，请联系管理员", 
                    "系统错误", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Object loggedInUser = null;
            
            // 根据选择的用户类型进行不同的登录
            switch (userType) {
                case "用户":
                    User user = backendService.login(username, password);
                    if (user != null) {
                        new UserMainFrame(user).setVisible(true);
                        loggedInUser = user;
                    }
                    break;
                case "维修人员":
                    MaintenanceStaff staff = backendService.staffLogin(username, password);
                    if (staff != null) {
                        new StaffMainFrame(staff).setVisible(true);
                        loggedInUser = staff;
                    }
                    break;
                case "管理员":
                    Administrator admin = backendService.adminLogin(username, password);
                    if (admin != null) {
                        new AdminMainFrame(admin).setVisible(true);
                        loggedInUser = admin;
                    }
                    break;
            }
            
            if (loggedInUser != null) {
                // 关闭登录窗口
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "用户名或密码错误，或者用户类型选择不正确", 
                    "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "登录过程中发生错误: " + e.getMessage(), 
                "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRegisterDialog() {
        if (registerDialog == null) {
            registerDialog = new RegisterDialog(this);
            registerDialog.setBackendService(backendService);
        }
        registerDialog.setVisible(true);
    }
} 