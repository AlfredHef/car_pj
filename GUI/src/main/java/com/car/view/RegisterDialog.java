package com.car.view;

import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JComboBox<String> userTypeCombo;

    public RegisterDialog(JFrame parent) {
        super(parent, "用户注册", true);
        initializeUI();
    }

    private void initializeUI() {
        setSize(400, 500);
        setLocationRelativeTo(getOwner());
        setResizable(false);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

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

        // 姓名输入
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(new JLabel("姓名:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(20);
        mainPanel.add(nameField, gbc);

        // 电话输入
        gbc.gridx = 0;
        gbc.gridy = 4;
        mainPanel.add(new JLabel("电话:"), gbc);
        gbc.gridx = 1;
        phoneField = new JTextField(20);
        mainPanel.add(phoneField, gbc);

        // 邮箱输入
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(new JLabel("邮箱:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(20);
        mainPanel.add(emailField, gbc);

        // 地址输入
        gbc.gridx = 0;
        gbc.gridy = 6;
        mainPanel.add(new JLabel("地址:"), gbc);
        gbc.gridx = 1;
        addressField = new JTextField(20);
        mainPanel.add(addressField, gbc);

        // 按钮面板
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton registerButton = new JButton("注册");
        registerButton.addActionListener(e -> handleRegister());
        buttonPanel.add(registerButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        mainPanel.add(buttonPanel, gbc);

        add(mainPanel);
    }

    private void handleRegister() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String userType = (String) userTypeCombo.getSelectedItem();

        // 验证输入
        if (username.isEmpty() || password.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请填写必填字段", "注册失败", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // TODO: 实现注册逻辑
        JOptionPane.showMessageDialog(this, "注册功能待实现", "提示", JOptionPane.INFORMATION_MESSAGE);
    }
} 