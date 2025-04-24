package com.car.view;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> userTypeCombo;

    public LoginFrame() {
        initializeUI();
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

        // TODO: 实现登录逻辑
        JOptionPane.showMessageDialog(this, "登录功能待实现", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showRegisterDialog() {
        new RegisterDialog(this).setVisible(true);
    }
} 