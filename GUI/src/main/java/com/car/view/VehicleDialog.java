package com.car.view;

import com.car.service.BackendService;
import com.carpj.model.User;
import com.carpj.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDateTime;

@Component
public class VehicleDialog extends JDialog {
    private JTextField licensePlateField;
    private JTextField brandField;
    private JTextField modelField;
    private JTextField yearField;
    private JTextField colorField;
    private JTextField vinField;
    
    @Autowired
    private BackendService backendService;
    
    private User currentUser;
    private Vehicle currentVehicle;
    private JFrame parentFrame;
    private boolean isEditMode = false;
    
    // Define UI colors to match with other components
    private final Color PRIMARY_COLOR = new Color(70, 130, 180); // Steel blue
    private final Color SECONDARY_COLOR = new Color(240, 248, 255); // Alice blue
    private final Color ACCENT_COLOR = new Color(30, 144, 255); // Dodger blue
    private final Color BG_COLOR = new Color(245, 245, 250); // Light grayish blue
    private final Color TEXT_COLOR = new Color(25, 25, 25); // Near black
    
    // 无参构造函数，供Spring使用
    public VehicleDialog() {
        this(null, null);
    }
    
    public VehicleDialog(JFrame parent, User user) {
        super(parent, "车辆信息", true);
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
    }
    
    public void setCurrentUser(User user) {
        this.currentUser = user;
        initializeUI();
    }
    
    /**
     * 设置编辑模式，并加载车辆信息
     * @param vehicle 待编辑的车辆
     */
    public void setEditMode(Vehicle vehicle) {
        this.isEditMode = true;
        this.currentVehicle = vehicle;
        setTitle("编辑车辆信息");
        
        if (licensePlateField != null) {
            // 已初始化UI，填充数据
            fillVehicleData();
        }
    }
    
    private void initializeUI() {
        setSize(450, 500);
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
        
        JLabel titleLabel = new JLabel(isEditMode ? "编辑车辆信息" : "添加新车辆");
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
        gbc.gridwidth = 1;

        // 车牌号
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel licensePlateLabel = createStyledLabel("车牌号:");
        formPanel.add(licensePlateLabel, gbc);
        
        gbc.gridx = 1;
        licensePlateField = createStyledTextField();
        formPanel.add(licensePlateField, gbc);

        // 品牌
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel brandLabel = createStyledLabel("品牌:");
        formPanel.add(brandLabel, gbc);
        
        gbc.gridx = 1;
        brandField = createStyledTextField();
        formPanel.add(brandField, gbc);

        // 型号
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel modelLabel = createStyledLabel("型号:");
        formPanel.add(modelLabel, gbc);
        
        gbc.gridx = 1;
        modelField = createStyledTextField();
        formPanel.add(modelField, gbc);

        // 年份
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel yearLabel = createStyledLabel("年份:");
        formPanel.add(yearLabel, gbc);
        
        gbc.gridx = 1;
        yearField = createStyledTextField();
        formPanel.add(yearField, gbc);

        // 颜色
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel colorLabel = createStyledLabel("颜色:");
        formPanel.add(colorLabel, gbc);
        
        gbc.gridx = 1;
        colorField = createStyledTextField();
        formPanel.add(colorField, gbc);

        // VIN码
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel vinLabel = createStyledLabel("VIN码:");
        formPanel.add(vinLabel, gbc);
        
        gbc.gridx = 1;
        vinField = createStyledTextField();
        formPanel.add(vinField, gbc);

        contentPane.add(formPanel, BorderLayout.CENTER);
        
        // Add note about required fields
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        notePanel.setOpaque(false);
        JLabel noteLabel = new JLabel("* 车牌号、品牌和型号为必填项");
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
        
        JButton saveButton = createStyledButton("保存", ACCENT_COLOR);
        saveButton.addActionListener(e -> handleSave());
        
        JButton cancelButton = createStyledButton("取消", new Color(100, 100, 100));
        cancelButton.addActionListener(e -> dispose());
        
        buttonRow.add(saveButton);
        buttonRow.add(cancelButton);
        
        buttonPanel.add(notePanel);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(buttonRow);
        
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        
        // 如果是编辑模式，填充数据
        if (isEditMode && currentVehicle != null) {
            fillVehicleData();
        }
    }
    
    private void fillVehicleData() {
        if (currentVehicle == null) return;
        
        licensePlateField.setText(currentVehicle.getLicensePlate());
        brandField.setText(currentVehicle.getBrand());
        modelField.setText(currentVehicle.getModel());
        
        if (currentVehicle.getYear() != null) {
            yearField.setText(currentVehicle.getYear().toString());
        }
        
        if (currentVehicle.getColor() != null) {
            colorField.setText(currentVehicle.getColor());
        }
        
        if (currentVehicle.getVin() != null) {
            vinField.setText(currentVehicle.getVin());
        }
        
        // 编辑模式下不允许修改车牌号
        licensePlateField.setEditable(false);
        licensePlateField.setBackground(new Color(240, 240, 240));
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

    private void handleSave() {
        // 验证必填字段
        String licensePlate = licensePlateField.getText().trim();
        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        
        if (licensePlate.isEmpty()) {
            showErrorMessage("请输入车牌号", "验证失败");
            return;
        }
        
        if (brand.isEmpty()) {
            showErrorMessage("请输入品牌", "验证失败");
            return;
        }
        
        if (model.isEmpty()) {
            showErrorMessage("请输入型号", "验证失败");
            return;
        }
        
        try {
            Vehicle vehicle;
            if (isEditMode && currentVehicle != null) {
                // 编辑模式，更新现有车辆
                vehicle = currentVehicle;
            } else {
                // 新建模式，创建新车辆
                vehicle = new Vehicle();
                vehicle.setLicensePlate(licensePlate);
                vehicle.setRegistrationDate(LocalDateTime.now());
            }
            
            // 设置车辆信息
            vehicle.setBrand(brand);
            vehicle.setModel(model);
            
            // 设置可选字段
            String yearStr = yearField.getText().trim();
            if (!yearStr.isEmpty()) {
                try {
                    vehicle.setYear(Integer.parseInt(yearStr));
                } catch (NumberFormatException e) {
                    showErrorMessage("年份必须是数字", "验证失败");
                    return;
                }
            }
            
            String color = colorField.getText().trim();
            if (!color.isEmpty()) {
                vehicle.setColor(color);
            }
            
            String vin = vinField.getText().trim();
            if (!vin.isEmpty()) {
                vehicle.setVin(vin);
            }
            
            // 设置车辆所有者
            if (currentUser != null) {
                vehicle.setUser(currentUser);
            } else {
                showErrorMessage("无法获取当前用户信息", "保存失败");
                return;
            }
            
            // 保存到后端
            if (backendService == null) {
                showErrorMessage("后端服务尚未初始化", "服务错误");
                return;
            }
            
            Vehicle savedVehicle;
            if (isEditMode) {
                savedVehicle = backendService.updateVehicle(vehicle);
            } else {
                savedVehicle = backendService.addVehicle(vehicle);
            }
            
            if (savedVehicle != null) {
                String message = isEditMode ? "车辆信息已成功更新" : "车辆已成功添加";
                showSuccessMessage(message, "操作成功");
                dispose();
            } else {
                showErrorMessage("保存车辆信息失败，请稍后再试", "保存失败");
            }
            
        } catch (Exception e) {
            showErrorMessage("保存过程中发生错误: " + e.getMessage(), "错误");
        }
    }
    
    private void showErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    private void showSuccessMessage(String message, String title) {
        JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
} 