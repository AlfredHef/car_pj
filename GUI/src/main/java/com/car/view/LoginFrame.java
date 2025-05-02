package com.car.view;

import com.car.service.BackendService;
import com.carpj.model.Administrator;
import com.carpj.model.MaintenanceStaff;
import com.carpj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    // Define UI colors
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice blue
    private final Color BUTTON_COLOR = new Color(30, 144, 255); // Dodger blue
    private final Color TEXT_COLOR = new Color(25, 25, 25); // Near black

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
        
        // 获取屏幕尺寸
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // 设置窗口大小为屏幕的一半
        setSize(screenSize.width / 2, screenSize.height / 2);
        // 设置窗口位置在屏幕中央
        setLocation(screenSize.width / 4, screenSize.height / 4);
        setResizable(true);
        
        // Set custom background for JFrame content pane
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gp = new GradientPaint(0, 0, SECONDARY_COLOR, 0, getHeight(), 
                        new Color(230, 230, 250)); // Lavender
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
        // Create a header panel with system name
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(20, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("车辆维修管理系统");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 24));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel);
        
        // Main login panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(0, 50, 20, 50));
        
        // Center the main panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(mainPanel, gbc);
        
        // User type selector panel
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel, BoxLayout.X_AXIS));
        typePanel.setOpaque(false);
        typePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JLabel typeLabel = new JLabel("用户类型:");
        typeLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        typeLabel.setForeground(TEXT_COLOR);
        typeLabel.setPreferredSize(new Dimension(80, 30));
        
        userTypeCombo = new JComboBox<>(new String[]{"用户", "维修人员", "管理员"});
        userTypeCombo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        userTypeCombo.setBackground(Color.WHITE);
        
        typePanel.add(typeLabel);
        typePanel.add(Box.createHorizontalStrut(10));
        typePanel.add(userTypeCombo);
        
        // Username panel
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setOpaque(false);
        usernamePanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        
        JLabel usernameLabel = new JLabel("用户名:");
        usernameLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        usernameLabel.setForeground(TEXT_COLOR);
        usernameLabel.setPreferredSize(new Dimension(80, 30));
        
        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        usernamePanel.add(usernameLabel);
        usernamePanel.add(Box.createHorizontalStrut(10));
        usernamePanel.add(usernameField);
        
        // Password panel
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setOpaque(false);
        passwordPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        passwordLabel.setForeground(TEXT_COLOR);
        passwordLabel.setPreferredSize(new Dimension(80, 30));
        
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalStrut(10));
        passwordPanel.add(passwordField);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 20, 0));
        
        JButton loginButton = createStyledButton("登录", BUTTON_COLOR);
        loginButton.addActionListener(e -> handleLogin());
        
        JButton registerButton = createStyledButton("注册新用户", new Color(60, 179, 113)); // Medium sea green
        registerButton.addActionListener(e -> showRegisterDialog());
        
        // Center the buttons
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setOpaque(false);
        buttonRow.add(loginButton);
        buttonRow.add(registerButton);
        
        buttonPanel.add(buttonRow);
        
        // Add all panels to main panel
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(typePanel);
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        // Add components to content pane
        contentPane.add(headerPanel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        
        // Add a footer
        JPanel footerPanel = new JPanel();
        footerPanel.setOpaque(false);
        footerPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        JLabel footerLabel = new JLabel("© 2023 车辆维修管理系统");
        footerLabel.setForeground(new Color(100, 100, 100));
        footerPanel.add(footerLabel);
        
        contentPane.add(footerPanel, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Paint button background with rounded corners
                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 40));
        
        return button;
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String userType = (String) userTypeCombo.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            showErrorMessage("用户名和密码不能为空", "登录失败");
            return;
        }

        try {
            if (backendService == null) {
                showErrorMessage("后端服务尚未初始化，请联系管理员", "系统错误");
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
                showErrorMessage("用户名或密码错误，或者用户类型选择不正确", "登录失败");
            }
        } catch (Exception e) {
            showErrorMessage("登录过程中发生错误: " + e.getMessage(), "错误");
        }
    }
    
    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }

    private void showRegisterDialog() {
        if (registerDialog == null) {
            registerDialog = new RegisterDialog(this);
            registerDialog.setBackendService(backendService);
        }
        registerDialog.setVisible(true);
    }
}