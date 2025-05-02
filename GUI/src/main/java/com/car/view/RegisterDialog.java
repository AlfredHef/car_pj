package com.car.view;

import com.car.service.BackendService;
import com.carpj.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;

@Component
public class RegisterDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextField addressField;
    private JComboBox<String> userTypeCombo;
    
    @Autowired
    private BackendService backendService;
    
    private JFrame parentFrame;
    
    // Define UI colors to match with other components
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodger blue
    private final Color BG_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(25, 25, 25); // Near black

    // 无参构造函数，供Spring使用
    public RegisterDialog() {
        this(null);
    }

    public RegisterDialog(JFrame parent) {
        super(parent, "用户注册", true);
        this.parentFrame = parent;
        initializeUI();
    }
    
    @PostConstruct
    private void init() {
        // Spring注入完成后的初始化
        if (parentFrame == null) {
            // 如果是被Spring直接创建，默认设置父窗口
            setLocationRelativeTo(null);
        }
    }

    public void setBackendService(BackendService backendService) {
        this.backendService = backendService;
    }

    private void initializeUI() {
        setSize(450, 550);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        
        // Set custom background for content pane
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient background
                GradientPaint gp = new GradientPaint(0, 0, SECONDARY_COLOR, 0, getHeight(), 
                        BG_COLOR);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Header panel with title
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(20, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("用户注册");
        titleLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 22));
        titleLabel.setForeground(PRIMARY_COLOR);
        headerPanel.add(titleLabel);
        
        contentPane.add(headerPanel, BorderLayout.NORTH);
        
        // Main form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(10, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // 用户类型选择
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel typeLabel = createStyledLabel("用户类型:");
        formPanel.add(typeLabel, gbc);
        
        gbc.gridx = 1;
        userTypeCombo = new JComboBox<>(new String[]{"用户", "维修人员", "管理员"});
        userTypeCombo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        userTypeCombo.setBackground(Color.WHITE);
        formPanel.add(userTypeCombo, gbc);

        // 用户名输入
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(createStyledLabel("用户名:"), gbc);
        
        gbc.gridx = 1;
        usernameField = createStyledTextField();
        formPanel.add(usernameField, gbc);

        // 密码输入
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(createStyledLabel("密码:"), gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        formPanel.add(passwordField, gbc);

        // 姓名输入
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(createStyledLabel("姓名:"), gbc);
        
        gbc.gridx = 1;
        nameField = createStyledTextField();
        formPanel.add(nameField, gbc);

        // 电话输入
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(createStyledLabel("电话:"), gbc);
        
        gbc.gridx = 1;
        phoneField = createStyledTextField();
        formPanel.add(phoneField, gbc);

        // 邮箱输入
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(createStyledLabel("邮箱:"), gbc);
        
        gbc.gridx = 1;
        emailField = createStyledTextField();
        formPanel.add(emailField, gbc);

        // 地址输入
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(createStyledLabel("地址:"), gbc);
        
        gbc.gridx = 1;
        addressField = createStyledTextField();
        formPanel.add(addressField, gbc);

        contentPane.add(formPanel, BorderLayout.CENTER);
        
        // Add note about required fields
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notePanel.setOpaque(false);
        JLabel noteLabel = new JLabel("* 用户名、密码和姓名为必填项");
        noteLabel.setFont(new Font("Microsoft YaHei", Font.ITALIC, 12));
        noteLabel.setForeground(new Color(150, 150, 150));
        notePanel.add(noteLabel);
        
        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(0, 0, 25, 0));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonRow.setOpaque(false);
        
        JButton registerButton = createStyledButton("注册", ACCENT_COLOR);
        registerButton.addActionListener(e -> handleRegister());
        
        JButton cancelButton = createStyledButton("取消", new Color(100, 100, 100));
        cancelButton.addActionListener(e -> dispose());
        
        buttonRow.add(registerButton);
        buttonRow.add(cancelButton);
        
        buttonPanel.add(notePanel);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(buttonRow);
        
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        label.setForeground(TEXT_COLOR);
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return textField;
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
        button.setPreferredSize(new Dimension(120, 40));
        
        return button;
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
            showErrorMessage("用户名、密码和姓名为必填字段", "注册失败");
            return;
        }

        try {
            if (backendService == null) {
                showErrorMessage("后端服务尚未初始化，请联系管理员", "系统错误");
                return;
            }
            
            // 创建用户对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setPhone(phone);
            user.setEmail(email);
            user.setAddress(address);
            user.setRegistrationDate(LocalDateTime.now());
            
            // 注册用户
            User registeredUser = backendService.register(user);
            
            if (registeredUser != null) {
                showSuccessMessage("注册成功！请使用新账号登录。", "注册成功");
                dispose();
            } else {
                showErrorMessage("注册失败，请稍后再试", "注册失败");
            }
        } catch (Exception e) {
            showErrorMessage("注册过程中发生错误: " + e.getMessage(), "错误");
        }
    }
    
    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccessMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
} 