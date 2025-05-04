package com.car.view;

import com.car.service.BackendService;
import com.carpj.model.RepairRequest;
import com.carpj.model.User;
import com.carpj.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class RepairRequestDialog extends JDialog {
    private JComboBox<VehicleItem> vehicleCombo;
    private JTextArea issueDescriptionArea;
    private JCheckBox urgentCheckbox;
    private JComboBox<String> priorityCombo;
    private JTextField preferredDateField;
    private JTextArea notesArea;
    
    @Autowired
    private BackendService backendService;
    
    private User currentUser;
    private JFrame parentFrame;
    
    // Define UI colors to match with other components
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodger blue
    private final Color BG_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(25, 25, 25); // Near black
    
    // 无参构造函数，供Spring使用
    public RepairRequestDialog() {
        this(null, null);
    }
    
    public RepairRequestDialog(JFrame parent, User user) {
        super(parent, "提交维修申请", true);
        this.parentFrame = parent;
        this.currentUser = user;
        
        // 如果非Spring创建时，直接初始化界面
        if (parent != null) {
            initializeUI();
        }
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
        // 如果服务可用，加载用户的车辆信息
        if (backendService != null && currentUser != null) {
            loadUserVehicles();
        }
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
        initializeUI();
    }
    
    private void initializeUI() {
        setSize(500, 650);
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
        
        JLabel titleLabel = new JLabel("提交维修申请");
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

        // 车辆选择
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel vehicleLabel = createStyledLabel("选择车辆:");
        formPanel.add(vehicleLabel, gbc);
        
        gbc.gridx = 1;
        vehicleCombo = new JComboBox<>();
        vehicleCombo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        vehicleCombo.setBackground(Color.WHITE);
        formPanel.add(vehicleCombo, gbc);

        // 问题描述
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel descriptionLabel = createStyledLabel("问题描述:");
        formPanel.add(descriptionLabel, gbc);
        
        gbc.gridx = 1;
        issueDescriptionArea = new JTextArea();
        issueDescriptionArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        issueDescriptionArea.setLineWrap(true);
        issueDescriptionArea.setWrapStyleWord(true);
        issueDescriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        
        JScrollPane descScrollPane = new JScrollPane(issueDescriptionArea);
        descScrollPane.setPreferredSize(new Dimension(0, 100));
        descScrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true));
        formPanel.add(descScrollPane, gbc);

        // 紧急标志
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        urgentCheckbox = new JCheckBox("紧急维修");
        urgentCheckbox.setFont(new Font("Microsoft YaHei", Font.BOLD, 14));
        urgentCheckbox.setOpaque(false);
        urgentCheckbox.setForeground(Color.RED);
        formPanel.add(urgentCheckbox, gbc);
        
        // 优先级
        gbc.gridx = 1;
        JPanel priorityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        priorityPanel.setOpaque(false);
        
        JLabel priorityLabel = createStyledLabel("优先级:");
        priorityCombo = new JComboBox<>(new String[]{"低", "中", "高"});
        priorityCombo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        priorityCombo.setBackground(Color.WHITE);
        priorityCombo.setSelectedIndex(1); // 默认选中"中"
        
        priorityPanel.add(priorityLabel);
        priorityPanel.add(Box.createHorizontalStrut(10));
        priorityPanel.add(priorityCombo);
        
        formPanel.add(priorityPanel, gbc);

        // 期望日期
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel dateLabel = createStyledLabel("期望日期:");
        formPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        preferredDateField = createStyledTextField();
        
        // 设置默认日期为明天，格式为YYYY-MM-DD
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        preferredDateField.setText(LocalDateTime.now().plusDays(1).format(formatter));
        
        formPanel.add(preferredDateField, gbc);

        // 备注
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        JLabel notesLabel = createStyledLabel("备注:");
        formPanel.add(notesLabel, gbc);
        
        gbc.gridx = 1;
        notesArea = new JTextArea();
        notesArea.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        notesArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        notesScrollPane.setPreferredSize(new Dimension(0, 80));
        notesScrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1, true));
        formPanel.add(notesScrollPane, gbc);

        contentPane.add(formPanel, BorderLayout.CENTER);
        
        // Add note about required fields
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notePanel.setOpaque(false);
        JLabel noteLabel = new JLabel("* 车辆选择和问题描述为必填项");
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
        
        JButton submitButton = createStyledButton("提交", ACCENT_COLOR);
        submitButton.addActionListener(e -> handleSubmit());
        
        JButton cancelButton = createStyledButton("取消", new Color(100, 100, 100));
        cancelButton.addActionListener(e -> dispose());
        
        buttonRow.add(submitButton);
        buttonRow.add(cancelButton);
        
        buttonPanel.add(notePanel);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(buttonRow);
        
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void loadUserVehicles() {
        try {
            if (backendService == null) {
                showErrorMessage("后端服务尚未初始化", "服务错误");
                return;
            }
            
            List<Vehicle> vehicles = backendService.getUserVehicles(currentUser.getId());
            
            if (vehicles == null || vehicles.isEmpty()) {
                showErrorMessage("您暂无注册车辆，请先添加车辆", "无可用车辆");
                // For testing purposes, add a demo vehicle
                Vehicle demoVehicle = new Vehicle();
                demoVehicle.setId(1L);
                demoVehicle.setLicensePlate("示例车辆 - 京A12345");
                demoVehicle.setBrand("大众");
                demoVehicle.setModel("途观");
                demoVehicle.setUser(currentUser);
                
                List<Vehicle> demoVehicles = new ArrayList<>();
                demoVehicles.add(demoVehicle);
                vehicles = demoVehicles;
            }
            
            // 清空并添加车辆选项
            vehicleCombo.removeAllItems();
            for (Vehicle vehicle : vehicles) {
                vehicleCombo.addItem(new VehicleItem(vehicle));
            }
            
        } catch (Exception e) {
            showErrorMessage("加载车辆信息失败: " + e.getMessage(), "错误");
        }
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

    private void handleSubmit() {
        if (vehicleCombo.getSelectedItem() == null) {
            showErrorMessage("请选择车辆", "提交失败");
            return;
        }
        
        String issueDescription = issueDescriptionArea.getText().trim();
        if (issueDescription.isEmpty()) {
            showErrorMessage("请填写问题描述", "提交失败");
            return;
        }
        
        try {
            if (backendService == null) {
                showErrorMessage("后端服务尚未初始化，请联系管理员", "系统错误");
                return;
            }
            
            // 创建维修申请
            RepairRequest repairRequest = new RepairRequest();
            repairRequest.setUser(currentUser);
            
            // 获取选中的车辆
            VehicleItem selectedVehicle = (VehicleItem) vehicleCombo.getSelectedItem();
            repairRequest.setVehicle(selectedVehicle.getVehicle());
            
            // 设置问题描述
            repairRequest.setIssueDescription(issueDescription);
            
            // 设置是否紧急
            repairRequest.setUrgent(urgentCheckbox.isSelected());
            
            // 设置优先级
            String priority = (String) priorityCombo.getSelectedItem();
            switch (priority) {
                case "低":
                    repairRequest.setPriority(RepairRequest.Priority.LOW);
                    break;
                case "高":
                    repairRequest.setPriority(RepairRequest.Priority.HIGH);
                    break;
                default:
                    repairRequest.setPriority(RepairRequest.Priority.MEDIUM);
                    break;
            }
            
            // 设置期望日期
            String preferredDateStr = preferredDateField.getText().trim();
            if (!preferredDateStr.isEmpty()) {
                try {
                    // 解析日期格式为YYYY-MM-DD
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDateTime preferredDate = LocalDateTime.parse(preferredDateStr + " 00:00", 
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    repairRequest.setPreferredDate(preferredDate);
                } catch (Exception e) {
                    showErrorMessage("期望日期格式不正确，请使用YYYY-MM-DD格式", "格式错误");
                    return;
                }
            }
            
            // 设置备注
            repairRequest.setNotes(notesArea.getText().trim());
            
            // 提交申请
            RepairRequest submittedRequest = backendService.submitRepairRequest(repairRequest);
            
            if (submittedRequest != null) {
                showSuccessMessage("维修申请已成功提交！工作人员会尽快处理。", "申请提交成功");
                dispose();
            } else {
                showErrorMessage("提交申请失败，请稍后再试", "提交失败");
            }
            
        } catch (Exception e) {
            showErrorMessage("提交过程中发生错误: " + e.getMessage(), "错误");
        }
    }
    
    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccessMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * 用于在下拉框中显示车辆信息的辅助类
     */
    private static class VehicleItem {
        private Vehicle vehicle;
        
        public VehicleItem(Vehicle vehicle) {
            this.vehicle = vehicle;
        }
        
        public Vehicle getVehicle() {
            return vehicle;
        }
        
        @Override
        public String toString() {
            return vehicle.getLicensePlate() + " - " + vehicle.getBrand() + " " + vehicle.getModel();
        }
    }
} 